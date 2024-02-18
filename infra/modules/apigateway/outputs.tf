output "user_url" {
  value = "${aws_api_gateway_stage.stage.invoke_url}/users"
}

output "address_url" {
  value = "${aws_api_gateway_stage.stage.invoke_url}/addresses"
}

# Display the SQS queue URL & API Gateway invokation URL
output "sqs_user" {
  value       = var.sqs_user_id
  description = "The SQS Queue URL"
}

output "sqs_address" {
  value       = var.sqs_address_id
  description = "The SQS Queue URL"
}

output "api_gtw_url" {
  value       = aws_api_gateway_stage.stage.invoke_url
  description = "The API Gateway Invocation URL Queue URL"
}

# Command for testing to send data to api gateway
output "Test-Command1" {
  value       = "curl --location --request POST '${aws_api_gateway_stage.stage.invoke_url}/users' --header 'Content-Type: application/json'  --data-raw '{ \"TestMessage\": \"Hello From ApiGateway!\" }'"
  description = "Command to invoke the API Gateway"
}

# Command for testing to retrieve the message from the SQS queue
output "Test-Command2" {
  value       = "aws sqs receive-message --queue-url ${var.sqs_user_id}"
  description = "Command to query the SQS Queue for messages"
}

output "Test-Command3" {
  value       = "aws sqs receive-message --queue-url ${var.sqs_address_id}"
  description = "Command to query the SQS Queue for messages"
}
