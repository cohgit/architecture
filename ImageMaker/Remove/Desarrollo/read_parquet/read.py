import awswrangler as wr

df = wr.s3.read_parquet(path='s3://dbs3export/prod-02-2023-10-19-23-11/remove_prd/')
print(df)  # Smaller Pandas DataFrame