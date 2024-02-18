# Create an IAM role for API Gateway
resource "aws_iam_role" "APIGatewayRole" {
  assume_role_policy = <<POLICY1
{
  "Version" : "2012-10-17",
  "Statement" : [
    {
      "Effect" : "Allow",
      "Principal" : {
        "Service" : "apigateway.amazonaws.com"
      },
      "Action" : "sts:AssumeRole"
    }
  ]
}
POLICY1
}

# Create an IAM policy for API Gateway to send messages to SQS
resource "aws_iam_policy" "APIGatewayPolicy" {
  policy = <<POLICY2
{
  "Version" : "2012-10-17",
  "Statement" : [
    {
      "Effect" : "Allow",
      "Action" : [
        "sqs:SendMessage",
        "sqs:SendMessageBatch"
      ],
      "Resource" : [
        "${var.sqs_user_arn}",
        "${var.sqs_address_arn}"
      ]
    }
  ]
}
POLICY2
}

# Attach the IAM policies to the equivalent rule
resource "aws_iam_role_policy_attachment" "APIGWPolicyAttachment" {
  role       = aws_iam_role.APIGatewayRole.name
  policy_arn = aws_iam_policy.APIGatewayPolicy.arn
}

#Create a new API Gateway rest api with SQS Integration
resource "aws_api_gateway_rest_api" "api" {
  name = "API Gateway SQS Serverless Pattern Demo"
  body = jsonencode({
    "openapi" : "3.0.1",
    "info" : {
      "title" : "APIGW SQS Serverless Pattern Demo",
      "version" : "2022-03-03T00:00:00Z"
    },
    "servers" : [{
      "variables" : {
        "basePath" : {
          "api" : "/api"
        }
      }
    }],
    "paths" : {
      "/users" : {
        "post" : {
          "responses" : {
            "200" : {
              "description" : "200 response",
              "content" : {
                "application/json" : {
                  "schema" : {
                    "$ref" : "#/components/schemas/Empty"
                  }
                }
              }
            }
          },
          "x-amazon-apigateway-integration" : {
            "type" : "aws",
            "credentials" : "${aws_iam_role.APIGatewayRole.arn}",
            "httpMethod" : "POST",
            "uri" : "arn:aws:apigateway:${data.aws_region.current.name}:sqs:path/${data.aws_caller_identity.current.account_id}/${var.sqs_user_name}",
            "responses" : {
              "default" : {
                "statusCode" : "200"
              }
            },
            "requestParameters" : {
              "integration.request.header.Content-Type" : "'application/x-www-form-urlencoded'"
            },
            "requestTemplates" : {
              "application/json" : "Action=SendMessage&MessageBody=$input.body"
            },
            "passthroughBehavior" : "never"
          }
        }
      },
      "/addresses" : {
        "post" : {
          "responses" : {
            "200" : {
              "description" : "200 response",
              "content" : {
                "application/json" : {
                  "schema" : {
                    "$ref" : "#/components/schemas/Empty"
                  }
                }
              }
            }
          },
          "x-amazon-apigateway-integration" : {
            "type" : "aws",
            "credentials" : "${aws_iam_role.APIGatewayRole.arn}",
            "httpMethod" : "POST",
            "uri" : "arn:aws:apigateway:${data.aws_region.current.name}:sqs:path/${data.aws_caller_identity.current.account_id}/${var.sqs_address_name}",
            "responses" : {
              "default" : {
                "statusCode" : "200"
              }
            },
            "requestParameters" : {
              "integration.request.header.Content-Type" : "'application/x-www-form-urlencoded'"
            },
            "requestTemplates" : {
              "application/json" : "Action=SendMessage&MessageBody=$input.body"
            },
            "passthroughBehavior" : "never"
          }
        }
      }
    },
    "components" : {
      "schemas" : {
        "Empty" : {
          "title" : "Empty Schema",
          "type" : "object"
        }
      }
    }
  })
}

# Create a new API Gateway deployment for the created rest api
resource "aws_api_gateway_deployment" "deployment" {
  rest_api_id = aws_api_gateway_rest_api.api.id
}

# Create a new API Gateway stage with logging enabled
resource "aws_api_gateway_stage" "stage" {
  deployment_id = aws_api_gateway_deployment.deployment.id
  rest_api_id   = aws_api_gateway_rest_api.api.id
  stage_name    = "api"
}

# Configure API Gateway to push all logs to CloudWatch Logs
resource "aws_api_gateway_method_settings" "setting" {
  rest_api_id = aws_api_gateway_rest_api.api.id
  stage_name  = aws_api_gateway_stage.stage.stage_name
  method_path = "*/*"

  settings {
    # Enable CloudWatch logging and metrics
    metrics_enabled = true
    logging_level   = "INFO"
  }
}
