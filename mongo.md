
## Conexión mongosh
mongosh "mongodb+srv://wom-architecture.zjo6pib.mongodb.net/sample_airbnb" --apiVersion 1 --username caogaldh
password : 1209%40mongo


## Arquitectura Mongo Atlas DB
Dentro del marco del proyecto Wompay donde está la premisa de usar servicio Cloud's AWS para la solución, es que se evalua el consumo e integración de datos bajo el servicio de persistencia mongo atlas.

## Contexto
Servicio de bases de datos modalidad SaaS integrada con datos provistos como input a la solución.

## Entradas de datos
Las entradas de datos consideradas son:
1. Archivo CVS provisto por protocolo SFTP.
1. Otras Bases de datos mongo.
1. Servicios API.

## Integración de datos CSV vía SFTP
se toma la premisa de cargas de datos cerca de la base de datos esto es en un área de procesamiento del SaaS MongoDB.

1. App service de Mongo
1. Trigger gatillado por eventos en la BDD
1. Trigger gatillado por agendamiento  

## Escenario de carga de información
el escenario consiste en una carga diaria de datos desde un archivo entregado vía SFTP conectado por certificado.

## Solución de Arquitectura POC
Utilizar procesamiento Trigger Mongo Altlas agendado diariamente para obtener el archivo y cargar.

## Trigger

Ver referencia mongo atlas [Referencia Mongo Atlas](https://www.mongodb.com/docs/atlas/app-services/triggers/examples/create-a-scheduled-trigger/)

Para revisar la versión de node debes ejecutar la siguiente función

```javascript
exports = function() {
  console.log(process.version);
  return process.version
};
```
```
> result: 
"v10.18.1"
```
esto nos indica en caso de revisar compatibilidades

Se prueba la conexión con las siguientes librerías de ssh de node todas Fallidas

[librería node-scp npm](https://www.npmjs.com/package/node-scp)

```javascript
const { Client } = require('node-scp')
async function test() {
  try {
    const client = await Client({
        host: 'ec2-X-XX-XXX-XXX.compute-1.amazonaws.com',
        port: 22,
        username: 'ec2-user',
        privateKey:
`-----BEGIN RSA PRIVATE KEY-----
... Key
-----END RSA PRIVATE KEY-----`
    });
    const result = await client.exists('./ssh2-promise-sample/sample.json');
    console.log(result);
    client.close() // remember to close connection after you finish
  } catch (e) {
    console.log(e);
  }
}
test();
```
El error 
```
> error: 
failed to execute source for 'node_modules/node-scp/lib/index.js': FunctionError: failed to execute source for 'node_modules/ssh2/lib/index.js': FunctionError: failed to execute source for 'node_modules/ssh2/lib/agent.js': FunctionError: failed to compile source for module './protocol/keyParser.js': SyntaxError: node_modules/ssh2/lib/protocol/keyParser.js: Line 177:61 Unexpected token ILLEGAL (and 4 more errors)
```

[librería ssh2-promise npm](https://www.npmjs.com/package/ssh2-promise)

```javascript
/** No Soportada por MongoDB */
console.log("required libray");
var SSH2Promise = require('ssh2-promise');

console.log("ssh config");
var sshconfig = {
    host: 'ec2-X-XX-XXX-XXX.compute-1.amazonaws.com',
    username: 'ec2-user',
    privateKey : 
`-----BEGIN RSA PRIVATE KEY-----
....
-----END RSA PRIVATE KEY-----`
};

console.log("create promise");
var ssh = new SSH2Promise(sshconfig);
console.log("try connect pivote");
ssh.connect().then(() => {
    console.log("Connection established");
}).catch(()=> {
    console.log("Connection failed");
});
console.log("exit");
```

Error al cargar la dependencia en mongo atlas

```
Failed to install dependencies
failed to transpile node_modules/ssh2-promise/spec/sftp-spec.js. "ssh2-promise" is likely not supported yet. unknown: Legacy octal literals are not allowed in strict mode (130:52)
```

[librería ssh2-sftp-client npm](https://www.npmjs.com/package/ssh2-sftp-client)

```javascript
const config = {
    host: 'ec2-XX-XXX-XX-XXX.compute-1.amazonaws.com',
    port: '22',
    username: 'ec2-user',
    debug: (msg) => {
        if (msg.startsWith('CLIENT')) {
          console.error(msg);
        }
    },
    passphrase : '',
    privateKey : 
`-----BEGIN RSA PRIVATE KEY-----
...
-----END RSA PRIVATE KEY-----`
};

console.log('require');
const Client = require('ssh2-sftp-client');
console.log('new Client');
let sftp = new Client();
console.log('connect ssh');

async function main() {
    console.log('await connect ssh');
    await sftp.connect(config);
    let fileList = await sftp.list('/home/ec2-user/');
    console.log(fileList);
    await sftp.end();
  }

main().catch((e) => {
    console.error(e.message);
});
```
Error
```
> error: 
failed to execute source for 'node_modules/ssh2-sftp-client/src/index.js': FunctionError: failed to execute source for 'node_modules/ssh2/lib/index.js': FunctionError: failed to execute source for 'node_modules/ssh2/lib/agent.js': FunctionError: failed to compile source for module './protocol/keyParser.js': SyntaxError: node_modules/ssh2/lib/protocol/keyParser.js: Line 177:61 Unexpected token ILLEGAL (and 4 more errors)

```

[libreria ssh2 npm](https://www.npmjs.com/package/ssh2)

[git ssh2 con mas documentación](https://github.com/mscdex/ssh2)

```javascript
const { Client } = require('ssh2');

const conn = new Client();
conn.on('ready', () => {
  console.log('Client :: ready');
  conn.sftp((err, sftp) => {
    if (err) throw err;
    sftp.readdir('/home/ec2-user/', (err, list) => {
      if (err) throw err;
      console.dir(list);
      conn.end();
    });
  });
}).connect({
    host: 'ec2-XX-XXX-XX-XXX.compute-1.amazonaws.com',
    port: 22,
    username: 'ec2-user',
    privateKey : 
`-----BEGIN RSA PRIVATE KEY-----
....
-----END RSA PRIVATE KEY-----`
});
```

Error

```
> error: 
failed to execute source for 'node_modules/ssh2/lib/index.js': FunctionError: failed to execute source for 'node_modules/ssh2/lib/agent.js': FunctionError: failed to compile source for module './protocol/keyParser.js': SyntaxError: node_modules/ssh2/lib/protocol/keyParser.js: Line 177:61 Unexpected token ILLEGAL (and 4 more errors)
```

Todos los códigos funcionaron en un entorno Node.js local con la misma versión sin embargo con serverless de mongo atlas no, debemos considerar otra solución para la carga de datos.

