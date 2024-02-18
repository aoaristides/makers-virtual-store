# Variáveis Globais
variable "aws_account_id" {
  type        = string
  description = "Código da conta AWS"
  default     = "000000000000"
}

variable "environment" {
  type        = string
  description = "Ambiente de execução: dev, hom, prod"
  default     = "dev"
}
