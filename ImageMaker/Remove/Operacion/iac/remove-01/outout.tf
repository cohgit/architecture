output "connect_pivot_ssh" {
  value       = "ssh -i ${aws_instance.pivot_instance.key_name}.pem ec2-user@${aws_instance.pivot_instance.public_dns}"
}