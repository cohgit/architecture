output taskcommand {
  /**
  esta información es la necesaria para ejecutar comando de tarea 
  aws rds start-export-task 
    --export-task-identifier prod-02-2024-04-16-23-10-s3-export 
    --source-arn arn:aws:rds:us-east-1:253021683072:snapshot:rds:prod-02-2024-04-16-23-10 
    --s3-bucket-name dbs3export 
    --iam-role-arn arn:aws:iam::253021683072:role/iamroles3bck 
    --kms-key-id arn:aws:kms:us-east-1:253021683072:key/8430d0fb-0d96-4cc7-976b-7c479a556cb9 
  */
  value       = "aws rds start-export-task --export-task-identifier -s3-export --source-arn  --s3-bucket-name ${aws_s3_bucket.s3bck.bucket} --iam-role-arn ${aws_iam_role.iamrole.arn} --kms-key-id ${aws_kms_key.kmskey.arn}"
 }
