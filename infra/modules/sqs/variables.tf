# Variáveis SQS
variable "sqs_name" {
  type        = string
  description = "Nome da Fila SQS que sera criada"
}

variable "delay_seconds" {
  type        = number
  default     = 0
  description = "Quantidade de segundos antes de deixar a mensagem visivel para consumo. Minimo: 0, Máximo: 900"
}

variable "max_message_size" {
  type        = number
  default     = 262144
  description = "Tamanho máximo em bytes do corpo da mensagem. Minimo: 1, Máximo: 262144"
}

variable "message_retention_seconds" {
  type        = number
  default     = 345600
  description = "Quantidade de tempo que a mensagem ficara na fila, senão excluida explicitamente. Minimo: 60, Máximo: 345600"
}

variable "receive_wait_time_seconds" {
  type        = number
  default     = 10
  description = "Tempo esperado pelo consumidor para receber uma mensagem. Habilita ou desabilita Long Polling. Minimo: 0, Máximo: 20"
}

variable "content_based_deduplication" {
  description = "Enables content-based deduplication for FIFO queues"
  type        = bool
  default     = false
}

variable "kms_master_key_id_sns" {
  description = "The ID of an AWS-managed customer master key (CMK) for Amazon SNS or a Customer Managed Key"
  type        = string
  default     = "alias/aws/sns"
}

variable "kms_master_key_id_sqs" {
  description = "The ID of an AWS-managed customer master key (CMK) for Amazon SQS or a Customer Managed Key"
  type        = string
  default     = "alias/aws/sqs"
}

variable "kms_master_key_id" {
  type        = string
  description = "Chave KMS para criptografia das mensagens tráfegadas via AWS SQS"
}

variable "kms_data_key_reuse_period_seconds" {
  type        = number
  default     = 300
  description = "Tempo em segundos que a chave KMS sera utilizada sem que uma nova solicitação seja feita."
}

variable "sqs_data_classification" {
  type        = string
  default     = "Interna"
  description = "Classificação do tipo de dado"
}

variable "visibility_timeout_seconds" {
  type        = number
  default     = 30
  description = "Tempo em que a mensagem ficara em processamento e, portanto, invisível para outros consumidores. Minimo: 0, Máximo: 43200"
}

variable "fifo_queue" {
  type        = bool
  default     = false
  description = "Flag para controle que define se esta fila é uma fila FIFO ou não. Se `true`, o nome da fila deve ter o sufixo .fifo"
}

variable "tags" {
  type        = map(any)
  description = "Tags obrigatórias"
  default     = {}
}

variable "alarm_sns_topic_arn" {
  description = "ARN of the SNS topic for alarm notifactions"
  default     = null
}

variable "allowed_arns" {
  description = "A list of AWS account IDs allowed to access this resource"
  type        = list(any)
  default     = null
}

variable "allowed_items_max" {
  description = "The maximum number of items allowed on the SQS queue before it triggers an alarm"
  default     = 50
}

locals {
  account_id = data.aws_caller_identity.current.account_id
}

data "aws_caller_identity" "current" {}
