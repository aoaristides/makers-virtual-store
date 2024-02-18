output "iam_role_arn" {
  value = aws_iam_role.APIGatewayRole.arn
}

output "iam_policy_arn" {
  value = aws_iam_policy.APIGatewayPolicy.arn
}
