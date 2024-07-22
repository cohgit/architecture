Para implementar la misma solución en PHP utilizando AWS Lambda, primero, necesitarás crear un paquete Lambda en PHP. AWS Lambda no soporta PHP de manera nativa, así que necesitas empaquetar el runtime de PHP junto con tu código y dependencias. Aquí tienes una guía paso a paso:

### 1. Preparar el Entorno PHP para Lambda

1. **Instalar PHP y AWS SDK para PHP:**

   Asegúrate de tener Composer instalado. Luego, crea un directorio de proyecto y añade el SDK de AWS para PHP.

   ```sh
   mkdir lambda-php
   cd lambda-php
   composer require aws/aws-sdk-php
   ```

2. **Crear el Archivo `index.php` con el Código Lambda:**

   Crea un archivo `index.php` en el directorio del proyecto con el siguiente contenido:

   ```php
   <?php
   require 'vendor/autoload.php';

   use Aws\Rds\RdsClient;
   use Aws\S3\S3Client;
   use Aws\Exception\AwsException;

   function lambdaHandler($event, $context) {
       $rdsClient = new RdsClient([
           'region' => 'us-east-1',
           'version' => 'latest',
       ]);

       $s3Client = new S3Client([
           'region' => 'us-east-1',
           'version' => 'latest',
       ]);

       $dbInstanceIdentifier = 'prod-02';
       $s3BucketName = 'dbs3export';
       $iamRoleArn = 'arn:aws:iam::253021683072:role/iamroles3bck';
       $kmsKeyId = 'arn:aws:kms:us-east-1:253021683072:key/be6a11f7-16d9-4ee2-bc0c-eab9e87bf7f1';

       // Verificar si el bucket está vacío
       $result = $s3Client->listObjectsV2([
           'Bucket' => $s3BucketName,
           'Prefix' => 'exported-snapshots/',
       ]);
       $isBucketEmpty = !isset($result['Contents']);
       $snapshotType = $isBucketEmpty ? 'full' : 'incremental';

       // Crear un nuevo snapshot
       $snapshotIdentifier = "prod-02-snapshot-" . date('Y-m-d-H-i-s') . "-$snapshotType";
       try {
           $rdsClient->createDBSnapshot([
               'DBSnapshotIdentifier' => $snapshotIdentifier,
               'DBInstanceIdentifier' => $dbInstanceIdentifier,
           ]);
       } catch (AwsException $e) {
           return [
               'statusCode' => 500,
               'body' => $e->getMessage(),
           ];
       }

       // Exportar el snapshot a S3
       try {
           $exportTaskId = 'task-' . date('YmdHis');
           $sourceArn = "arn:aws:rds:us-east-1:253021683072:snapshot:$snapshotIdentifier";
           $rdsClient->startExportTask([
               'ExportTaskIdentifier' => $exportTaskId,
               'SourceArn' => $sourceArn,
               'S3BucketName' => $s3BucketName,
               'IamRoleArn' => $iamRoleArn,
               'KmsKeyId' => $kmsKeyId,
               'S3Prefix' => 'exported-snapshots/',
           ]);
           return [
               'statusCode' => 200,
               'body' => "Successfully started $snapshotType export task $exportTaskId.",
           ];
       } catch (AwsException $e) {
           return [
               'statusCode' => 500,
               'body' => $e->getMessage(),
           ];
       }
   }
   ```

3. **Crear el Archivo `bootstrap` para el Runtime:**

   Crea un archivo llamado `bootstrap` en el mismo directorio:

   ```sh
   #!/bin/sh
   php /var/task/index.php
   ```

   Asegúrate de que el archivo sea ejecutable:

   ```sh
   chmod +x bootstrap
   ```

### 2. Empaquetar el Código para Lambda

1. **Crear un Archivo ZIP con el Código y Dependencias:**

   ```sh
   zip -r function.zip .
   ```

### 3. Desplegar la Función Lambda

1. **Crear y Configurar el Rol IAM:**

   ```sh
   aws iam create-role --role-name lambda-execution-role --assume-role-policy-document file://trust-policy.json

   aws iam attach-role-policy --role-name lambda-execution-role --policy-arn arn:aws:iam::aws:policy/service-role/AWSLambdaBasicExecutionRole
   aws iam attach-role-policy --role-name lambda-execution-role --policy-arn arn:aws:iam::aws:policy/AmazonRDSFullAccess
   aws iam attach-role-policy --role-name lambda-execution-role --policy-arn arn:aws:iam::aws:policy/AmazonS3FullAccess
   aws iam attach-role-policy --role-name lambda-execution-role --policy-arn arn:aws:iam::aws:policy/AWSKeyManagementServicePowerUser
   ```

2. **Desplegar la Función Lambda:**

   ```sh
   ROLE_ARN=$(aws iam get-role --role-name lambda-execution-role --query 'Role.Arn' --output text)
   aws lambda create-function --function-name export-rds-snapshot --runtime provided --role $ROLE_ARN --handler bootstrap --zip-file fileb://function.zip
   ```

### 4. Configurar el Disparador de CloudWatch Events

1. **Crear la Regla de CloudWatch Events:**

   ```sh
   aws events put-rule --name daily-rds-snapshot-export --schedule-expression 'rate(1 day)'
   ```

2. **Agregar Permisos para CloudWatch Events:**

   ```sh
   aws lambda add-permission --function-name export-rds-snapshot --statement-id daily-rds-snapshot-export --action 'lambda:InvokeFunction' --principal events.amazonaws.com --source-arn arn:aws:events:REGION:ACCOUNT_ID:rule/daily-rds-snapshot-export
   ```

3. **Asignar la Regla de CloudWatch Events a la Función Lambda:**

   ```sh
   FUNCTION_ARN=$(aws lambda get-function --function-name export-rds-snapshot --query 'Configuration.FunctionArn' --output text)
   aws events put-targets --rule daily-rds-snapshot-export --targets "Id"="1","Arn"="$FUNCTION_ARN"
   ```

### Verificar y Monitorear

1. **Probar la Función Manualmente:**

   ```sh
   aws lambda invoke --function-name export-rds-snapshot output.txt
   ```

2. **Verificar los Logs de Ejecución:**

   ```sh
   aws logs describe-log-groups
   aws logs describe-log-streams --log-group-name /aws/lambda/export-rds-snapshot
   aws logs get-log-events --log-group-name /aws/lambda/export-rds-snapshot --log-stream-name <log-stream-name>
   ```

### Mantenimiento

Para actualizar el código de la función Lambda o realizar ajustes en los permisos, sigue los mismos pasos descritos anteriormente:

1. **Actualizar el Código:**

   Edita el archivo `index.php` según sea necesario.

2. **Crear un Nuevo Archivo ZIP:**

   ```sh
   zip -r function.zip .
   ```

3. **Actualizar la Función Lambda:**

   ```sh
   aws lambda update-function-code --function-name export-rds-snapshot --zip-file fileb://function.zip
   ```

4. **Probar y Verificar:**

   ```sh
   aws lambda invoke --function-name export-rds-snapshot output.txt
   ```

### Resumen de Funcionalidad

- La función Lambda verifica si el bucket S3 está vacío.
- Si el bucket está vacío, se realiza un snapshot completo y se exporta.
- Si el bucket contiene datos, se realiza un snapshot incremental y se exporta.

