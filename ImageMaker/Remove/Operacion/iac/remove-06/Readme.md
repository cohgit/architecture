## El objetivo de este proyecto es poder exportar snapshot rds prostgress y poder acceder a el desde un programa exterior
Basado en la doucmentación : https://registry.terraform.io/providers/hashicorp/aws/latest/docs/resources/rds_export_task

### Codigo para obtener ultimo snapshot de prod-02
```console
aws rds describe-db-snapshots --filters Name=db-instance-id,Values=prod-02 --query="max_by(DBSnapshots, &SnapshotCreateTime).DBSnapshotArn" --output text
```

arn:aws:rds:us-east-1:253021683072:snapshot:rds:prod-02-2024-04-16-23-10

### Codigo para iniciar una exportación en s3
```console
aws rds start-export-task --export-task-identifier prod-02-2024-04-16-23-10-s3-export --source-arn arn:aws:rds:us-east-1:253021683072:snapshot:rds:prod-02-2024-04-16-23-10 --s3-bucket-name dbs3export --iam-role-arn arn:aws:iam::253021683072:role/iamroles3bck --kms-key-id arn:aws:kms:us-east-1:253021683072:key/8430d0fb-0d96-4cc7-976b-7c479a556cb9

```
