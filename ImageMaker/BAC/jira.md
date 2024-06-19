## Requerimiento obtencióm de publicaciones 

## Se Solicita Token a Roberto Rozas
ATATT3xFfGF05PFfNVS1mzk1TJQ-MfVtQts-n6IIhqrBw2L6sMt8sxvxC9IOWVYrJt7M0la643SN7CkfYmtueqUKQAmkxiQJ3NZ24Pbwmg8lzpHb-VtC1t-AhBIBUsZDQ8rK_0QSysl_PBkNjNn8Ty4hvUaEasC0410PY1dcmWGPU4ddptWPpfg=C55B82AB

## Prueba del token
Funciona
curl -v https://bac-latam.atlassian.net --user roberto.rozas@cr.asesorexternoca.com:ATATT3xFfGF05PFfNVS1mzk1TJQ-MfVtQts-n6IIhqrBw2L6sMt8sxvxC9IOWVYrJt7M0la643SN7CkfYmtueqUKQAmkxiQJ3NZ24Pbwmg8lzpHb-VtC1t-AhBIBUsZDQ8rK_0QSysl_PBkNjNn8Ty4hvUaEasC0410PY1dcmWGPU4ddptWPpfg=C55B82AB

## generación de Authorization Bearer
En linux usa el comando
```console
 echo -n 'roberto.rozas@cr.asesorexternoca.com:ATATT3xFfGF05PFfNVS1mzk1TJQ-MfVtQts-n6IIhqrBw2L6sMt8sxvxC9IOWVYrJt7M0la643SN7CkfYmtueqUKQAmkxiQJ3NZ24Pbwmg8lzpHb-VtC1t-AhBIBUsZDQ8rK_0QSysl_PBkNjNn8Ty4hvUaEasC0410PY1dcmWGPU4ddptWPpfg=C55B82AB' | openssl base64 -A
```

Salida :
cm9iZXJ0by5yb3phc0Bjci5hc2Vzb3JleHRlcm5vY2EuY29tOkFUQVRUM3hGZkdGMDVQRmZOVlMxbXprMVRKUS1NZlZ0UXRzLW42SUlocXJCdzJMNnNNdDhzeHZ4QzlJT1dWWXJKdDdNMGxhNjQzU043Q2tmWW10dWVxVUtRQW1reGlRSjNOWjI0UGJ3bWc4bHpwSGItVnRDMXQtQWhCSUJVc1pEUThyS18wUVN5c2xfUEJrTmpObjhUeTRodlVhRWFzQzA0MTBQWTFkY21XR1BVNGRkcHRXUHBmZz1DNTVCODJBQg==



curl --request GET --url 'https://bac-latam.atlassian.net/rest/api/2/project/EDPEH' --user 'email@example.com:<api_token>roberto.rozas@cr.asesorexternoca.com:ATATT3xFfGF05PFfNVS1mzk1TJQ-MfVtQts-n6IIhqrBw2L6sMt8sxvxC9IOWVYrJt7M0la643SN7CkfYmtueqUKQAmkxiQJ3NZ24Pbwmg8lzpHb-VtC1t-AhBIBUsZDQ8rK_0QSysl_PBkNjNn8Ty4hvUaEasC0410PY1dcmWGPU4ddptWPpfg=C55B82AB' --header 'Accept: application/json'


curl --request GET --url 'https://bac-latam.atlassian.net/rest/api/2/project/EDPEH' --user 'roberto.rozas@cr.asesorexternoca.com:ATATT3xFfGF05PFfNVS1mzk1TJQ-MfVtQts-n6IIhqrBw2L6sMt8sxvxC9IOWVYrJt7M0la643SN7CkfYmtueqUKQAmkxiQJ3NZ24Pbwmg8lzpHb-VtC1t-AhBIBUsZDQ8rK_0QSysl_PBkNjNn8Ty4hvUaEasC0410PY1dcmWGPU4ddptWPpfg=C55B82AB' --header 'Accept: application/json'

