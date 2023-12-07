<?php

require 'vendor/autoload.php';

use Aws\S3\S3Client;
use codename\parquet\ParquetReader;

// Replace these with your own credentials and S3 details
$s3Config = [
    'version'     => 'latest',
    'region'      => 'us-east-1',
    'credentials' => [
        'key'    => 'AKIATV2KEPWAAKHQDIIX',
        'secret' => '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',
    ],
];

$s3Client = new S3Client($s3Config);

$bucketName = 'dbs3export';
$objectKey = 'prod-02-2023-10-19-23-11/remove_prd/remove.scanners/1/part-00000-3d1ecd78-85c7-426a-b910-c450ee5c8c46-c000.gz.parquet';

// Download Parquet file from S3 to a temporary local file
$tempLocalFile = tempnam(sys_get_temp_dir(), 'parquet');
$s3Client->getObject([
    'Bucket' => $bucketName,
    'Key'    => $objectKey,
    'SaveAs' => $tempLocalFile,
]);

$fileStream = fopen($tempLocalFile, 'r');

// Read Parquet file using php-parquet library
$parquetReader = new ParquetReader($fileStream);

$dataFields = $parquetReader->schema->GetDataFields();

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

    // $data member, accessible through ->getData() contains an array of column data
    $data = $columns[10]->getData();//." ".$columns[1]->getData();

    // Print data or do other stuff with it
    print_r($data);
 }


unlink($tempLocalFile);
?>