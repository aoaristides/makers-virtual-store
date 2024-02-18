output "queue_arn" {
  value = aws_sqs_queue.queue.arn
}

output "queue_name" {
  value = aws_sqs_queue.queue.name
}

output "queue_id" {
  value = aws_sqs_queue.queue.id
}

output "deadletter_queue_arn" {
  value = aws_sqs_queue.deadletter_queue.arn
}

output "deadletter_queue_name" {
  value = aws_sqs_queue.deadletter_queue.name
}

output "deadletter_queue_id" {
  value = aws_sqs_queue.deadletter_queue.id
}
