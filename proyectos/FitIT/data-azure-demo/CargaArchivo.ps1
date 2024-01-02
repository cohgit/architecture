Import-Module ImportExcel
Import-Module SqlServer

# Definir Parametros
$url = "./fuentes_datos/Parametrico zona comuna.xlsx"
$tablaSQL = "PARAMETRICO_COMUNA"
$RemoteSQLDatabase = 'ReportServer'
$RemoteSQLInstance = "vmsqldemo"
$RemoteSQLUser = 'report'
$RemoteSQLPassword = 'P@$$w0rd1234!'


echo "Validando si existe tabla"

# Verificar si la tabla existe
$tableExistsQuery = "SELECT COUNT(*) as count FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_CATALOG='$RemoteSQLDatabase' AND TABLE_SCHEMA='dbo' AND TABLE_NAME='$tablaSQL';"
$counts = Invoke-Sqlcmd -ServerInstance $RemoteSQLInstance -Database $RemoteSQLDatabase -Username $RemoteSQLUser -Password $RemoteSQLPassword -TrustServerCertificate -Query $tableExistsQuery
$tableExists = $counts.count -eq 1

echo "Tabla existe [$tableExists]"

# Si la tabla no existe, crearla
if (!$tableExists) {
    $createTableQuery = "CREATE TABLE dbo.$tablaSQL (ZONA NVARCHAR(255), LOCA NVARCHAR(255), COMUNA NVARCHAR(255));"
    Invoke-Sqlcmd -ServerInstance $RemoteSQLInstance -Database $RemoteSQLDatabase -Username $RemoteSQLUser -Password $RemoteSQLPassword -TrustServerCertificate -Query $createTableQuery
    echo "Tabla creada"
} 

echo 'Eliminando data'
$truncateTableQuery = "TRUNCATE TABLE $tablaSQL"
Invoke-Sqlcmd -ServerInstance $RemoteSQLInstance -Database $RemoteSQLDatabase -Username $RemoteSQLUser -Password $RemoteSQLPassword -TrustServerCertificate -Query $truncateTableQuery

echo "importando data desde excel"
$excelData = Import-Excel -Path $url -WorksheetName "Hoja1"

echo "Escribiendo Data en Tabla"

$password = ConvertTo-SecureString "$RemoteSQLPassword" -AsPlainText -Force
$cred = New-Object System.Management.Automation.PSCredential ("$RemoteSQLUser", $password)
$cred.Password.MakeReadOnly()
 
$excelData | Write-SqlTableData -Database $RemoteSQLDatabase -ServerInstance $RemoteSQLInstance -Credential $cred -TrustServerCertificate -TableName $tablaSQL -SchemaName 'dbo'

echo "Carga finalizada" 
