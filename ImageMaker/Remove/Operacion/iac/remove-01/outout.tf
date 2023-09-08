output "connect_pivot_ssh" {
  value       = "ssh -i ${aws_instance.pivot_instance.key_name}.pem ec2-user@${aws_instance.pivot_instance.public_dns}"
}
output "connect_pivot_prd_ssh" {
  value       = "ssh -i ${aws_instance.pivot_instance_prd.key_name}.pem ec2-user@${aws_instance.pivot_instance_prd.public_dns}"
}