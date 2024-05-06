## Instala SQL Server 
 docker run --name mssql2016 -e 'ACCEPT_EULA=Y' -e 'MSSQL_SA_PASSWORD=Passw0rd' -p 1433:143
3 -d mcr.microsoft.com/mssql/server:2017-latest



## Ejecuta SQL con Version
docker exec -it mssql2016 /opt/mssql-tools/bin/sqlcmd -S localhost -U SA -P 'Passw0rd' -Q 'SELECT @@VERSION'