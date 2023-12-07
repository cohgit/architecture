<?php
require 'vendor/autoload.php';

use codename\parquet\ParquetReader;


try {

    $fileStream = fopen('/private/var/folders/5j/yg6bb5_n38dc94vj30qh82vm0000gn/T/parquet_0iC6Kb', 'r');

    // open parquet file reader
    $parquetReader = new ParquetReader($fileStream);

    // get file schema (available straight after opening parquet reader)
    // however, get only data fields as only they contain data values
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
        // get first column, for instance
        $firstColumn = $columns[0];

        // $data member, accessible through ->getData() contains an array of column data
        $data = $firstColumn->getData();

        // Print data or do other stuff with it
        print_r($data);
    }

} catch (Exception $exception) {
    echo "Failed to list objects in $bucket_name with error: " . $exception->getMessage();
    exit("Please fix error with listing objects before continuing.");
}
?>

