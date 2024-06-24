# PRUEBA TÉCNICA 

## PERFIL WSO2 MICRO INTEGRATOR Fecha: Junio 2024

###  Parte 1: Integración y Almacenamiento de Datos: 
1. Consulta a API de Feriados
    1. Configurar WSO2 Micro Integrator para hacer Ilamadas periódicas al endpoint de feriados de Chile.
    1. Usar el endpoint https://apis.digital.gob.cl/fl/feriados/2023 que retorna los feriados del año en curso.

    #### Pasos para resolver este punto:
    * Abre el WSO2 Integrator Studio
    * Crea un Integrator Integrator Selecionado Create ESB Config y Create Composite Exporter
    * Crea un EndPoint (el nombre del archivo debe coincidir con el name)
    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <endpoint name="feriadosEndpoint" xmlns="http://ws.apache.org/ns/synapse">
        <http method="get" uri-template="https://apis.digital.gob.cl/fl/feriados/2024">
            <suspendOnFailure>
                <initialDuration>-1</initialDuration>
                <progressionFactor>1.0</progressionFactor>
            </suspendOnFailure>
            <markForSuspension>
                <retriesBeforeSuspension>0</retriesBeforeSuspension>
            </markForSuspension>
        </http>
    </endpoint>
    ```
    * crea una secuencia apuntando al endpoint
    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <sequence name="feriadosSecuence" trace="disable" xmlns="http://ws.apache.org/ns/synapse">
        <send>
            <endpoint key="feriadosEndpoint"/>
        </send>
    </sequence>
    ```
    * crea un proxy-service para la llamada al servicio
    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <proxy name="feriadosProxyService" startOnLoad="true" transports="http https" xmlns="http://ws.apache.org/ns/synapse">
        <target>
            <inSequence>
                <sequence key="feriadosSecuence"/>
            </inSequence>
            <outSequence>
                <send/>
            </outSequence>
            <faultSequence/>
        </target>
    </proxy>
    ```


1. Transformación de Datos 
    1. Transformar la respuesta recibida del servicio de feriados para adaptarla a un esquema JSON especifico que podria incluir campos como nombreFeriado, fecha, tipo y descripción. 

#### Pasos para resolver este punto:
* Agrega la iteracion que cambia el payload de cada elemento con el siguiente código en el proxy-sevice antes de llamar al \<send\/\> dentro del \<outSequence\>.
```xml
<foreach expression="json-eval($)" id="foreach_1">
    <sequence>
        <payloadFactory media-type="json">
            <format>
                {
                    "nombreFeriado": "$1",
                    "fecha": "$2",
                    "tipo": "$3",
                    "descripcion": "$4"
                }
            </format>
            <args>
                <arg evaluator="json" expression="$.nombre"/>
                <arg evaluator="json" expression="$.fecha"/>
                <arg evaluator="json" expression="$.tipo"/>
                <arg evaluator="json" expression="$.comentarios"/>
            </args>
        </payloadFactory>
    </sequence>
</foreach>
```

1. Almacenamiento en Base de Datos: 
    1. Almacenar los datos transformados en una base de datos. Puedes elegir entre una base de datos SOL como MySOL o Postgres. 
    1. Asegurar que el proceso maneje correctamente conexiones y posibles excepciones durante las operaciones de la base de datos.
#### Pasos para resolver este punto:
* Crear una base de datos mysql 5.7 con docker en tu máquina y exporner el puerto de la base de datos. 
Usa las siguientes variable : 
    1. nombre del contenedor *prueba-mysql*
    1. puerto expouesto de la base *3306*
    1. password del root *my-secret-pw*
    1. usuario de conexion *prueba*
    1. password de usuario de conexion *prueba*
    1. base de datos llamada *test*
```console
$ docker run --name prueba-mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=my-secret-pw -e MYSQL_USER=prueba -e MYSQL_PASSWORD=prueba -e MYSQL_DATABASE=test -d mysql:5.7
```
* Descargar driver mysql my-secret-pw y pegarlo en lib
```console
/wso2mi-4.2.0/lib$ ls -la
total 2476
drwxrwxr-x  2 cogalde cogalde    4096 Jun 21 16:39 .
drwxrwxr-x 15 cogalde cogalde    4096 Jun 21 22:06 ..
-rwxr-xr-x  1 cogalde cogalde 2495536 Jun 21 16:39 mysql-connector-j-8.3.0.jar
```
* conectate a la base de datos y crea la base con el siguiente script del archivo script-db.sql
```sql
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
* agrega el siguiente código en el proxy-service, posterior a la transformación para guardar en la base de datos, la expresion expression="//fecha/child::text()" toma el elemento raiz de la iteracion obteniendo su nombre e hijo del tipo texto.
```xml
<dbreport>
    <connection>
        <pool>
            <driver>com.mysql.jdbc.Driver</driver>
            <url>jdbc:mysql://localhost:3306/test</url>
            <user>prueba</user>
            <password>prueba</password>
        </pool>
    </connection>
    <statement>
        <sql><![CDATA[insert into FERIADOS (nombre, fecha, tipo, descripcion) values (?, ?, ?, ?)]]></sql>
        <parameter expression="//nombreFeriado/child::text()" type="VARCHAR"/>
        <parameter expression="//fecha/child::text()" type="VARCHAR"/>
        <parameter expression="//tipo/child::text()" type="VARCHAR"/>
        <parameter expression="//comentarios/child::text()" type="VARCHAR"/>
    </statement>
</dbreport>
```
* Prueba 

### Parte 2: Exposición de API REST:
1. Desarrollo de API REST: 
    1. Utilizar WSO2 Micro Integrator para crear un servicio API REST. 
    1. Este servicio debe aceptar una fecha en formato yyyy-mm-dd como entrada y responder si es un feriado o no, consultando la base de datos.
#### Pasos para resolver este punto:
* Crear API el contexto será la url inicial, el nombre del API y el uri-template la continuación de la ruta ej. /api/esFeriado/2024-01-02
```xml
<?xml version="1.0" encoding="UTF-8"?>
<api context="/api" name="FeriadoAPI" xmlns="http://ws.apache.org/ns/synapse">
    <resource methods="GET" uri-template="/esFeriado/{fecha}">
        <inSequence>
            <!-- Extraer la fecha de la URL -->
            <property expression="get-property('uri.var.fecha')" name="fecha" scope="default" type="STRING"/>
            <!-- Registrar la fecha recibida -->
            <log level="full">
                <property expression="get-property('fecha')" name="Fecha Recibida"/>
            </log>
        </inSequence>
        <outSequence/>
        <faultSequence/>
    </resource>
</api>
```
* Luego agrega la consulta a la Base de datos luego del log, apuntando a la tabla que ya creamos anteriormente (debe tener datos)
```xml
<!-- Consulta a la base de datos para verificar si la fecha es un feriado -->
<dblookup>
    <connection>
        <pool>
            <driver>com.mysql.jdbc.Driver</driver>
            <url>jdbc:mysql://localhost:3306/test</url>
            <user>prueba</user>
            <password>prueba</password>
        </pool>
    </connection>
    <statement>
        <sql><![CDATA[SELECT nombre FROM FERIADOS WHERE fecha = ?]]></sql>
        <parameter expression="get-property('fecha')" type="VARCHAR"/>
        <result column="nombre" name="nombreFeriado"/>
    </statement>
</dblookup>
```
* Agregamos un filter para poder evaluar si obtuvo respuesta devolviendo un http 200 y si no devuelga un 404 not found
```xml
<!-- Evaluar el resultado y establecer la respuesta -->
<filter xpath="get-property('nombreFeriado') != ''">
    <then>
        <payloadFactory media-type="json">
            <format>
                {
                    "mensaje": "El día es un feriado"
                }
            </format>
            <args/>
        </payloadFactory>
        <respond/>
    </then>
    <else>
        <payloadFactory media-type="json">
            <format>
                {
                    "mensaje": "El día no es un feriado"
                }
            </format>
            <args/>
        </payloadFactory>
        <property name="HTTP_SC" scope="axis2" type="STRING" value="404"/>
        <respond/>
    </else>
</filter>
```

### Entregables
1. Código fuente del proyecto
1. Scripts de creación de la base de datos y cualquier script adicional necesario para la configuracion.
1. Documentacion de la API en formato swagger (yaml o json).

Archivo swagger API :
```yml
openapi: 3.0.0
info:
  title: API de Feriados
  version: 1.0.0
  description: Una API para verificar si una fecha dada es un feriado
paths:
  /api/esFeriado/{fecha}:
    get:
      summary: Verificar si una fecha es un feriado
      description: Devuelve un mensaje indicando si la fecha dada es un feriado
      parameters:
        - name: fecha
          in: path
          required: true
          schema:
            type: string
            format: date
            example: '2024-06-21'
          description: La fecha en formato yyyy-mm-dd
      responses:
        '200':
          description: La fecha es un feriado
          content:
            application/json:
              schema:
                type: object
                properties:
                  mensaje:
                    type: string
                    example: "El día es un feriado"
        '404':
          description: La fecha no es un feriado
          content:
            application/json:
              schema:
                type: object
                properties:
                  mensaje:
                    type: string
                    example: "El día no es un feriado"
servers:
  - url: http://localhost:8280
    description: WSO2 Micro Integrator

```