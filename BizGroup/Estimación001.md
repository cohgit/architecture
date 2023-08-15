# Creación y Modificación de API's
Stack Tecnologico : NetCore / IIS
Modalidad: Termino

## Requerimientos
Cada requerimiento debe contar con 
1. Pruebas Unitarias.
1. BDD PLSQL (script SQL).
1. Interfaz IIS con net Core.
1. Documentación (Swagger / MD / Doc).


### Caso 1. Modificación de API Agrega un campo GET / POST (8h)
Impacto: Cambio en IIS / Cambio en PL / Cambio del Swagger
Estimación (4h) GET
Estimación (4h) POST

Se solicita la modificación a API: https://ds-reembolsos.isapredecodelco.cl/swagger/ui/index

Endpoint: GET /api/Reembolsos/DatosGrupoFamiliarAfiliado
Modificación: Agregar el Numero interno de Documento
Nombre Campo: NumeroInternoDocumento
Tipo Campo: NUMBER

Endpoint: POST /api/Reembolsos/ValidaDocumentoReembolso
Modificación: Agregar el Numero interno de Documento
Nombre Campo: NumeroInternoDocumento
Tipo Campo: NUMBER


### Caso 2. Agregar PL al proceso EndPoint de las APIS. (40h)
Impacto : Agregar 3 llamadas al API definida (Ingreso, Salida y Error)

Actividades Por Endpoints (5h):
1. Analisis de los campos (1h)
1. Agregar llamadas (1h)
1. Elaborar Pruebas Unitarias (3h)

Lista de Endpoints (40h 8x5h)
1. GET /api/Reembolsos/InformaReembolso/
1. GET /api/Reembolsos/ObtieneImagen/
1. GET /api/Reembolsos/DatosGrupoFamiliarAfiliado/
1. POST /api/Reembolsos/GuardarDatosDigitados
1. POST /api/Reembolsos/ValidaDocumentoReembolso
1. GET /api/Reembolsos/InformaReembolsos
1. GET /api/Reembolsos/DatosSolicitud/
1. POST /api/SharedFiles/GuardarArchivo

### Caso 3. Construir un API/REST de un API SOAP/WSDL (20h)
Impacto: crear una nueva API que transforma un JSON a SOAP

crear API REST (10h)

### Caso 3. Construir un API/REST  (30h)
Impacto: crear una nueva API que transforma un JSON a SOAP

## Estimación (114h)
1. Setup de Ambiente de trabajo (8h)
    1. IDE
    1. IIS
    1. BDD Oracle
    1. Postman
1. Pruebas Desarrollo (8h)
1. Modificación caso 1 (8h)
1. Modificación caso 2 (40h)
1. Construcción caso 3 (20h)
1. Contrucción caso 4 (30h)

Estimación total (114h)