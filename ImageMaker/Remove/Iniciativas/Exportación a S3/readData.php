<?php
require 'vendor/autoload.php';

use Aws\S3\S3Client;
use Aws\Exception\AwsException;
use codename\parquet\ParquetReader;

$bucket_name = "dbs3export";
$s3client = new S3Client([ 'key' => 'AKIATV2KEPWAAKHQDIIX', 'secret' => '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', 'region' => 'us-east-1', 'version' => 'latest']);
$key = "prod-02-2023-10-19-23-11/remove_prd/remove.clients_plans/1/part-00000-3d1ecd78-85c7-426a-b910-c450ee5c8c46-c000.gz.parquet";

try {

    
    $contents = $s3client->listObjects([
        'Bucket' => $bucket_name,
    ]);
    
    echo "The contents of your bucket are: \n";
    foreach ($contents['Contents'] as $content) {
        echo $content['Key'] . "\n";
    }

    $result = $s3client->getObject([
        'Bucket' => $bucket_name,
        'Key'    => $key,
    ]);

    // Create a temporary file to store the downloaded Parquet file
    $tempFile = tempnam(sys_get_temp_dir(), 'parquet_');
    //print($result['Body']);
    file_put_contents($tempFile, $result['Body']);
    
    // open file stream (in this example for reading only)
    $fileStream = fopen($tempFile, 'r');
    print_r('Reading '.$tempFile);

    // open parquet file reader
    $parquetReader = new ParquetReader($fileStream);

    // enumerate through row groups in this file
    for($i = 0; $i < $parquetReader->getRowGroupCount(); $i++)
    {
        // create row group reader
        $groupReader = $parquetReader->OpenRowGroupReader($i);
        
        // read all columns inside each row group (you have an option to read only
        // required columns if you need to.
        $columns = [];
        foreach($dataFields as $field) {
            $columns[] = $groupReader->ReadColumn($field);
        }

        // get first column, for instance
        //$firstColumn = $columns[0];

        // $data member, accessible through ->getData() contains an array of column data
        //$data = $firstColumn->getData();

        // Print data or do other stuff with it
        //print_r($data);
    }


    //unlink($tempFile);

} catch (AwsException $e) {
    // Handle AWS errors
    echo "AWS Error: " . $e->getAwsErrorMessage();
} catch (Exception $exception) {
    echo "Failed to list objects in $bucket_name with error: " . $exception->getMessage();
    exit("Please fix error with listing objects before continuing.");
}
?>

