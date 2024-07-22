import boto3
import datetime

def lambda_handler(event, context):
    rds_client = boto3.client('rds')
    s3_client = boto3.client('s3')

    # Obtener el último snapshot de RDS
    snapshots = rds_client.describe_db_snapshots(
        DBInstanceIdentifier='prod-02',
        SnapshotType='manual'
    )
    latest_snapshot = max(snapshots['DBSnapshots'], key=lambda x: x['SnapshotCreateTime'])

    # Exportar el snapshot a S3
    export_task_id = f"task-{datetime.datetime.now().strftime('%Y%m%d%H%M%S')}"
    source_arn = latest_snapshot['DBSnapshotArn']
    s3_bucket_name = 'dbs3export'
    iam_role_arn = 'arn:aws:iam::253021683072:role/iamroles3bck'
    kms_key_id = 'arn:aws:kms:us-east-1:253021683072:key/be6a11f7-16d9-4ee2-bc0c-eab9e87bf7f1'
    
    rds_client.start_export_task(
        ExportTaskIdentifier=export_task_id,
        SourceArn=source_arn,
        S3BucketName=s3_bucket_name,
        IamRoleArn=iam_role_arn,
        KmsKeyId=kms_key_id,
        S3Prefix='exported-snapshots/'
    )

    # Eliminar los archivos Parquet anteriores en S3
    bucket_objects = s3_client.list_objects_v2(Bucket=s3_bucket_name, Prefix='exported-snapshots/')
    if 'Contents' in bucket_objects:
        for obj in bucket_objects['Contents']:
            if obj['Key'].endswith('.parquet'):
                s3_client.delete_object(Bucket=s3_bucket_name, Key=obj['Key'])

    return {
        'statusCode': 200,
        'body': f'Successfully started export task {export_task_id} and deleted old Parquet files.'
    }
