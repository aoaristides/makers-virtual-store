variable "sqs_user_name" {
  type = string
}

variable "sqs_user_id" {
  type = string
}

variable "sqs_user_arn" {
  type = string
}

variable "sqs_address_name" {
  type = string
}

variable "sqs_address_id" {
  type = string
}

variable "sqs_address_arn" {
  type = string
}

locals {
  account_id = data.aws_caller_identity.current.account_id
}

data "aws_caller_identity" "current" {}

data "aws_region" "current" {}

