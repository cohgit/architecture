# Exportar RDS to S3 Con lineas de Comando

Este documento muestra como poder generar una descarga desde un snapshot de RDS Postegress hacia S3

## Revisar último Snapshot

Para revisar el último snapshot debemos ejecutar el siguiente comando

db-instance-id = prod-02

```console
aws rds describe-db-snapshots --filters Name=db-instance-id,Values=prod-02 --query="max_by(DBSnapshots, &SnapshotCreateTime).DBSnapshotArn" --output text
```

AWSChatBot

```console
aws rds describe-db-snapshots --filters Name=db-instance-id,Values=prod-02 --query="DBSnapshots[?SnapshotCreateTime>='2024-01-20'].{DBSnapshotArn:DBSnapshotArn,SnapshotCreateTime:SnapshotCreateTime}" --output json

DBSnapshots | max_by(&SnapshotCreateTime).DBSnapshotArn

@aws rds describe-db-snapshots --db-instance-identifier prod-02 --snapshot-type automated --query @.{DBSnapshots:DBSnapshots[*].{SnapshotCreateTime:SnapshotCreateTime,DBSnapshotArn:DBSnapshotArn}}  --region us-east-1 --max-records 20


@aws rds describe-db-snapshots --db-instance-identifier prod-02 --snapshot-type automated --query max_by(@,&SnapshotCreateTime).DBSnapshotArn  --region us-east-1 --max-items 1


```





Esto devolverá el id del último snapshot existente

ejemplo

```console
arn:aws:rds:us-east-1:253021683072:snapshot:rds:prod-02-2024-01-21-23-10
```

## Ejecutar Tarea de descarga a S3

Este comando iniciará una tarea de descarga a s3 

export-task-identifier =  corresponde al id de la tarea 
source-arn = corresponde al id obtenido del último snapshot
s3-bucket-name = corresponde al id del bucket

```console
aws rds start-export-task --export-task-identifier task-20230122 --source-arn arn:aws:rds:us-east-1:253021683072:snapshot:rds:prod-02-2024-01-20-23-11 --s3-bucket-name dbs3export --iam-role-arn arn:aws:iam::253021683072:role/iamroles3bck --kms-key-id arn:aws:kms:us-east-1:253021683072:key/be6a11f7-16d9-4ee2-bc0c-eab9e87bf7f1
```







