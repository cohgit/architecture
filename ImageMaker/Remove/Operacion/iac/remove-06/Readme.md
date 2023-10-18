## El objetivo de este proyecto es poder exportar snapshot rds prostgress y poder acceder a el desde un programa exterior
Basado en la doucmentación : https://registry.terraform.io/providers/hashicorp/aws/latest/docs/resources/rds_export_task

### Codigo para obtener ultimo snapshot de prod-02
```console
aws rds describe-db-snapshots --filters Name=db-instance-id,Values=prod-02 --query="max_by(DBSnapshots, &SnapshotCreateTime).DBSnapshotArn" --output text
```

arn:aws:rds:us-east-1:253021683072:snapshot:rds:prod-02-2023-10-17-23-11

### Codigo para iniciar una exportación en s3
```console
aws rds start-export-task --export-task-identifier cmorell-s3-export --source-arn arn:aws:rds:us-east-1:253021683072:snapshot:rds:prod-02-2023-10-17-23-11 --s3-bucket-name cmorell-s3-export --iam-role-arn arn:aws:iam::123456789012:role/service-role/ExportRole --kms-key-id arn:aws:kms:us-west-2:123456789012:key/abcd0000-7fca-4128-82f2-aabbccddeeff

```
