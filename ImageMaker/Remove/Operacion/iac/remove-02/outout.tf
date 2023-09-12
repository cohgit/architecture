output "connect_pivot_prd_ssh" {
  value       = "ssh -i ${aws_instance.instance.key_name}.pem ec2-user@${aws_instance.instance.public_ip}"
}