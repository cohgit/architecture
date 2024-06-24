
## Arranca la Base de datos con docker

```console
$ docker run --name prueba-mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=my-secret-pw -e MYSQL_USER=prueba -e MYSQL_PASSWORD=prueba -e MYSQL_DATABASE=test -d mysql:5.7
```

## Crea la tabla del ejemplo

```console
CREATE TABLE test.FERIADOS (
	nombre varchar(100) NULL,
	fecha varchar(100) NULL,
	tipo varchar(100) NULL,
	descripcion varchar(100) NULL
)
ENGINE=InnoDB
DEFAULT CHARSET=latin1
COLLATE=latin1_swedish_ci;

```

## agrega esto para llamar a la BDD
```xml
    <dbreport>
        <connection>
            <pool>
                <password>prueba</password>
                <user>prueba</user>
                <url>jdbc:mysql://localhost:3306/test</url>
                <driver>com.mysql.jdbc.Driver</driver>
            </pool>
        </connection>
        <statement>
            <sql><![CDATA[insert into FERIADOS (nombre, fecha, tipo, descripcion) values (?, ?, ?, ?)]]></sql>
            <parameter expression="$.nombre" type="VARCHAR"/>
            <parameter expression="$.fecha" type="VARCHAR"/>
            <parameter expression="$.tipo" type="VARCHAR"/>
            <parameter expression="$.comentarios" type="VARCHAR"/>
        </statement>
    </dbreport>
```

## agrega el JAR al lib

```console
cogalde@IMCL-FCEF3:~/dev_p/wso2mi-4.2.0/lib$ pwd
/home/cogalde/dev_p/wso2mi-4.2.0/lib
cogalde@IMCL-FCEF3:~/dev_p/wso2mi-4.2.0/lib$ ls -la
total 2476
drwxrwxr-x  2 cogalde cogalde    4096 Jun 21 16:39 .
drwxrwxr-x 15 cogalde cogalde    4096 Jun 21 17:33 ..
-rwxr-xr-x  1 cogalde cogalde 2495536 Jun 21 16:39 mysql-connector-j-8.3.0.jar
-rw-r--r--  1 cogalde cogalde   25730 Oct 13  2023 org.wso2.micro.integrator.utils_4.2.0.17.jar
```
