module "sqs_users" {
  source = "./modules/sqs"

  sqs_name                          = "UserReceived"
  delay_seconds                     = 0
  max_message_size                  = 262144
  message_retention_seconds         = 345600
  receive_wait_time_seconds         = 10
  content_based_deduplication       = false
  kms_data_key_reuse_period_seconds = 300
  visibility_timeout_seconds        = 30
  fifo_queue                        = false
  kms_master_key_id                 = "teste-user"

  tags = {
    Environment             = "dev"
    tech-team-mail          = "contato@makersweb.com.br"
    sqs_data_classification = "Interna"
  }
}

module "sqs_address" {
  source = "./modules/sqs"

  sqs_name                          = "AddressReceived"
  delay_seconds                     = 0
  max_message_size                  = 262144
  message_retention_seconds         = 345600
  receive_wait_time_seconds         = 10
  content_based_deduplication       = false
  kms_data_key_reuse_period_seconds = 300
  visibility_timeout_seconds        = 30
  fifo_queue                        = false
  kms_master_key_id                 = "teste-address"

  tags = {
    Environment             = "dev"
    tech-team-mail          = "contato@makersweb.com.br"
    sqs_data_classification = "Interna"
  }
}

module "apigateway" {
  source = "./modules/apigateway"

  sqs_user_name    = module.sqs_users.queue_name
  sqs_user_id      = module.sqs_users.queue_id
  sqs_user_arn     = module.sqs_users.queue_arn
  sqs_address_name = module.sqs_address.queue_name
  sqs_address_id   = module.sqs_address.queue_id
  sqs_address_arn  = module.sqs_address.queue_arn
}

output "main_user_url" {
  description = "Endpoint do API Gateway"
  value       = module.apigateway.user_url
}

output "main_address_url" {
  description = "Endpoint do API Gateway"
  value       = module.apigateway.address_url
}

output "main_sqs_user" {
  description = "SQS Users"
  value       = module.apigateway.sqs_user
}

output "main_sqs_address" {
  description = "SQS Address"
  value       = module.apigateway.sqs_address
}
