Contactos:
Santiago Acevedo Villegas - sanaceve@bancolombia.com.co Consultor Banking as a Service 4046577 | +57 3114014064
Laura Maria Arango - lauraran@bancolombia.com.co 
Juan Pablo García - juanp.garcia@imagemaker.com  Área de Negocios +350 847 9412 | +305 766 4828


Credenciales Bancolombia
Basic Auth para Solicitar Token
username / Client id : d2ab6df5-ab9c-4c60-9767-f6db130352b8
password / Secret    : kW6hF5gI3iK6gN6eS2mI2xB0fN8gH3sI8qS8rF5xY5uY2hQ2uH

$echo -n 'd2ab6df5-ab9c-4c60-9767-f6db130352b8:kW6hF5gI3iK6gN6eS2mI2xB0fN8gH3sI8qS8rF5xY5uY2hQ2uH' | openssl base64

Salida
ZDJhYjZkZjUtYWI5Yy00YzYwLTk3NjctZjZkYjEzMDM1MmI4OmtXNmhGNWdJM2lLNmdONmVTMm1JMnhCMGZOOGdIM3NJOHFTOHJGNXhZNXVZMmhRMnVI

Ejemplo 
$curl --location --request POST 'https://api-sdx.apps.ambientesbc.com/ext/sandbox/v1/business-support/it-management/oauth2/token' --header 'Content-Type: application/x-www-form-urlencoded' --header 'Accept: application/json' --header 'Authorization: Basic ZDJhYjZkZjUtYWI5Yy00YzYwLTk3NjctZjZkYjEzMDM1MmI4OmtXNmhGNWdJM2lLNmdONmVTMm1JMnhCMGZOOGdIM3NJOHFTOHJGNXhZNXVZMmhRMnVI' --header 'Cookie: incap_ses_1270_2821027=LK/APnwKsXzs7Tdm9vKfEaop7mMAAAAA2DhdHbWo0zkjE7G/Y5tzhQ==; nlbi_2821027=cbhwdSxja10yObZnN0JGEgAAAADSNa9hZViaF3FM/zCspjOv; visid_incap_2772063=qN5jIM4+Qc6K386gznjUDWeI2WMAAAAAQUIPAAAAAAD3i7+55u+Z++jL9f5NvYmm; visid_incap_2821027=lxHY5gJIROKgHMlutwtXR/qE2WMAAAAAQUIPAAAAAAAb+kiIE6iAHO/IcDH/arSl' --data-urlencode 'grant_type=client_credentials' --data-urlencode 'scope=Customer-viability:read:app'


Consulta Valida Cliente

Crear MessageID 
$uuidgen
A889812C-8E72-434B-A88A-6CB59F9F819D

url --location --request POST 'https://api-sdx.apps.ambientesbc.com/ext/sandbox/v1/operations/cross-product/payments/bancolombiapay/validate' \
--header 'messageId: 4be85e92-7d51-4681-b4bd-5c36a8c277cc' \
--header 'Content-Type: application/json' \
--header 'Accept: application/vnd.bancolombia.v4+json' \
--header 'Authorization: Bearer AAIkZDJhYjZkZjUtYWI5Yy00YzYwLTk3NjctZjZkYjEzMDM1MmI4ilqvpvCtjpKmDFFpN_DS4B3I8Sek6M_ZqjZp9aoGCOhAWGtTK_Cs9ubjhvPApiI_iJNvtyTUzZ5gPJjz6k0LGtaqNoSc2fVqL3pEDjD9j2FI09znMcyL8e9EgmiH5d7JfR2vLywkwv23RvOlEci6vtSSNTb1TljEPpTJllKGEro' \
--header 'Cookie: incap_ses_1270_2821027=LK/APnwKsXzs7Tdm9vKfEaop7mMAAAAA2DhdHbWo0zkjE7G/Y5tzhQ==; nlbi_2821027=cbhwdSxja10yObZnN0JGEgAAAADSNa9hZViaF3FM/zCspjOv; visid_incap_2772063=qN5jIM4+Qc6K386gznjUDWeI2WMAAAAAQUIPAAAAAAD3i7+55u+Z++jL9f5NvYmm; visid_incap_2821027=lxHY5gJIROKgHMlutwtXR/qE2WMAAAAAQUIPAAAAAAAb+kiIE6iAHO/IcDH/arSl' \
--data-raw '{
  "data": {
    "customer": {
      "identification": {
        "type": "CC",
        "number": "34892702"
      }
    }
  }
}'