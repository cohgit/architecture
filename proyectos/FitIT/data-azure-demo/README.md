# Ambiente demo

Ambiente demo report server

Referencia : https://spgeeks.devoworx.com/install-and-configure-power-bi-report-server/


## Creación de infraestructura demo
1. IP Maquina RS : ¿? salida de terraform 40.83.180.183
1. Usuario : adminuser
1. Password : P@$$w0rd1234!
1. IP SQL Server : ¿? salida de terraform 10.0.2.5
1. Usuario : sqluser
1. Password : P@$$w0rd1234!

## PostConfiguración VM SQL Demo (rdp desde VM Demo)
1. Configuraciuón de cambio de autenticación de SQL Server a Mixto 
1. Agregar regla firewall inbound SQL Sever puerto 1433
1. Crear usuario report en SQL con SQL Server Authentication. 
    1. usuario : report
    1. password P@$$w0rd1234!
    1. tipo de autenticación SQL Authentication
    1. privilegios de sysadmin

# PostConfiguración VM Demo
1. Descargar e Instalar Power BI Report Service (Licencia de tipo evaluación 180 dias) con reinicio
```console
Invoke-WebRequest -Uri 'https://download.microsoft.com/download/7/0/9/709CBE7F-2005-4A83-B405-CA37A9AB8DC8/PowerBIReportServer.exe' -OutFile 'PowerBIReportServer.exe'
```
1. Selecciona Instancia vmdemo
1. Service Account Para el Servicio : 
1. Seleccciona BDD : 
    1. Agrega conexión a la ip 10.0.2.5 con report / P@$$w0rd1234!
    1. En credencilaes usa SQL Authentication con report / P@$$w0rd1234!
    1. Y finaliza el proceso

1. Descargar e Instalar Edge
```console
Invoke-WebRequest -Uri 'https://go.microsoft.com/fwlink/?linkid=2109047&Channel=Stable&language=en&consent=1' -OutFile 'Edge.exe'
```
1. Ingresa a http://vmdemo:80/Reports con adminuser P@$$w0rd1234!
1. Agregar regla firewall inbound HTTP Report Server puerto 80
1. Ingresa a http://40.83.180.183/Reports con adminuser P@$$w0rd1234!


# Carga de Archivos

1. Descarga en Los archivos a importar
1. Abriendo powershell importa los modulos necesarios
    1. Install-Module -Name ImportExcel -Force -Scope CurrentUser
    1. Install-Module -Name SqlServer -Force -Scope CurrentUser
1. crea el siguiente script en archivo CargaDatos.ps1 dentro de la carpeta de archivos
```console
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
$tableExistsQuery = "SELECT COUNT(*) as count FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_CATALOG='$basededatos' AND TABLE_SCHEMA='dbo' AND TABLE_NAME='$tablaSQL';"
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

```
1. Ejecuta script ingresando a la carpeta "C:\Users\adminuser\Downloads\fuentes_datos"
```console
> cd  C:\Users\adminuser\Downloads\fuentes_datos
```
2. Ejcuta el script
```console
  > .\CargaArchivo.ps1 
```

# SQL Studio (SSMS)

```console
Invoke-WebRequest -Uri 'https://download.microsoft.com/download/6/2/6/626e40a7-449e-418d-9726-33b523a1e336/SSMS-Setup-ENU.exe' -OutFile 'SSMS-Setup-ENU.exe'
```

# Usuarios
1. agrega un usuario al sistema
view
1209@Fitit
1. asigna rol en server