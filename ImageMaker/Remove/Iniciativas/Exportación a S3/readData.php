<?php
require 'vendor/autoload.php';

use Aws\S3\S3Client;
use Aws\Exception\AwsException;
use Parquet\ParquetReader;

$bucket_name = "dbs3export";
$s3client = new S3Client([ 'key' => 'AKIATV2KEPWAAKHQDIIX', 'secret' => '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', 'region' => 'us-east-1', 'version' => 'latest']);
$key = "prod-02-2023-10-19-23-11/remove_prd/remove.clients_plans/1/part-00000-3d1ecd78-85c7-426a-b910-c450ee5c8c46-c000.gz.parquet";

try {

    
    #$contents = $s3client->listObjects([
    #    'Bucket' => $bucket_name,
    #]);
    
    #echo "The contents of your bucket are: \n";
    #foreach ($contents['Contents'] as $content) {
    #    echo $content['Key'] . "\n";
    #}

    $result = $s3client->getObject([
        'Bucket' => $bucket_name,
        'Key'    => $key,
    ]);

    // Create a temporary file to store the downloaded Parquet file
    $tempFile = tempnam(sys_get_temp_dir(), 'parquet_');
    file_put_contents($tempFile, $result['Body']);
    
    // Read Parquet file
    $reader = new ParquetReader($tempFile);
    $schema = $reader->getSchema();

    prin($schema);

    unlink($tempFile);

} catch (AwsException $e) {
    // Handle AWS errors
    echo "AWS Error: " . $e->getAwsErrorMessage();
} catch (Exception $exception) {
    echo "Failed to list objects in $bucket_name with error: " . $exception->getMessage();
    exit("Please fix error with listing objects before continuing.");
}
?>

