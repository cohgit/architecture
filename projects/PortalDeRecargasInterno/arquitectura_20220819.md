Acuerdo de Arquitcetura

## Definción Arquitectura
Los proyectos deben aderirse a SSO toBe AWS Cloud

## Requerimiento proyecto
Sitio público sin identificación de cliented ni login

## Integraciones de servicios
Carlos Ramirez indica que no hay impacto que se puede usar token de anonimo

## POC validar token anonimo con los TMF del proyecto
Escenario : Generar Token anónimo y usar los TMF con ese token
Objetivo es validar no impacto en los TMF y validación del token sea correcta.

Dependiendo del resultado vemos desiciones con los GAP's.


Arquitectura Técnica
1. Uso publico de TMF sin usuario autenticado (sitio público).
1. Integración con devetel para los botones de pago que el tiene (documentación)
1. Buscar token en el onPremise esto cambiaría por el SSO Cloud si es que funciona con la POC, sino funcuona solicitaríamos token onpremise y quedamos con el GAP SSO Cloud.
1. Estará en la cuenta de AWS de E-Commerce.



## Log de SSO Prueba 1

POST http://localhost:8083/api/loginManager/anonymous: {
  "Network": {
    "addresses": {
      "local": {
        "address": "::1",
        "family": "IPv6",
        "port": 1658
      },
      "remote": {
        "address": "::1",
        "family": "IPv6",
        "port": 8083
      }
    }
  },
  "Request Headers": {
    "lm-api-key": "Oozv3FzlK6rEEyAI8xDzBbE42263Zqf9zaSRdAuNFsSD",
    "origin": "https://test.wom.cl",
    "user-agent": "PostmanRuntime/7.29.2",
    "accept": "*/*",
    "cache-control": "no-cache",
    "postman-token": "b2d6f55a-1bbe-47e8-b724-922cbe9fa31d",
    "host": "localhost:8083",
    "accept-encoding": "gzip, deflate, br",
    "connection": "keep-alive",
    "content-length": "0"
  },
  "Response Headers": {
    "server-timing": "intid;desc=119e8a777a5c7815",
    "vary": "Origin,Access-Control-Request-Method,Access-Control-Request-Headers",
    "access-control-allow-origin": "https://test.wom.cl",
    "access-control-allow-credentials": "true",
    "amazon-trace-id": "Root=1-630cefeb-87a572d64ec530dd3d2d4738; Parent=1acbf9deb3a01c02; Sampled=1",
    "content-type": "application/json",
    "content-length": "1237",
    "set-cookie": "wom_lm_at=eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJ3RVQtMEZmRGFKT1M3R2drUE53OUlWZjRvU05WSXhFbnVkYXZFbG9MSW13In0.eyJleHAiOjE2NjE3OTMxMzUsImlhdCI6MTY2MTc5MjIzNSwianRpIjoiNDU0MTQyZTctZTc2Yy00YTY3LTk1ZWQtZWNiNDAxMjAyMWUzIiwiaXNzIjoiaHR0cHM6Ly9xYS1sb2dpbi53b20uY2w6ODQ0My9hdXRoL3JlYWxtcy93b20iLCJhdWQiOiJhY2NvdW50Iiwic3ViIjoiM2JiZTI2MjMtYzQ4MS00MDMxLTk2MTItYmU0MTM3NTQyNDhmIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoid29tLWFub255bW91cyIsImFjciI6IjEiLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsiZGVmYXVsdC1yb2xlcy13b20iLCJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiIiLCJjbGllbnRJZCI6IndvbS1hbm9ueW1vdXMiLCJjbGllbnRIb3N0IjoiMTAuMTAuMi4xNzciLCJjbGllbnRBZGRyZXNzIjoiMTAuMTAuMi4xNzcifQ.M_0djSqJZ23minBcTjwDDWFfXC51b7NA1uWZkd0d6zbc2lwUmI8TM1sbmWwNe4tORVTlVD3SODFAj0xsNMfMLldUHp2B5Ilk4v83qRuycoSOg1JzPtS_YkStf3wzoRzfLDU1BYBDxvcLz-X6M8T5TLVQ4ZnEgJ4S_yCUnlgSXeMxBrBF7l5EuMeweA39RRsibonHbA8Mt4LCxk7aNRBSE1PpmoLi1HBF71czPoh8Jm453h4S6D8OzvU7JbCydqHk5vlYJOnqwCoqr8XzWUjqz11KdSzkLVo3yh2GCb02PN6pT0qYEsAff0Itj6c20doAtUId7oVP8Gfpl4TA5lipqw; Path=/; Domain=wom.cl; Max-Age=900; Expires=Mon, 29 Aug 2022 17:12:15 GMT; Secure; SameSite=None",
    "x-envoy-upstream-service-time": "31",
    "date": "Mon, 29 Aug 2022 16:57:15 GMT",
    "server": "envoy"
  },
  "Response Body": "{\"access_token\":\"eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJ3RVQtMEZmRGFKT1M3R2drUE53OUlWZjRvU05WSXhFbnVkYXZFbG9MSW13In0.eyJleHAiOjE2NjE3OTMxMzUsImlhdCI6MTY2MTc5MjIzNSwianRpIjoiNDU0MTQyZTctZTc2Yy00YTY3LTk1ZWQtZWNiNDAxMjAyMWUzIiwiaXNzIjoiaHR0cHM6Ly9xYS1sb2dpbi53b20uY2w6ODQ0My9hdXRoL3JlYWxtcy93b20iLCJhdWQiOiJhY2NvdW50Iiwic3ViIjoiM2JiZTI2MjMtYzQ4MS00MDMxLTk2MTItYmU0MTM3NTQyNDhmIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoid29tLWFub255bW91cyIsImFjciI6IjEiLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsiZGVmYXVsdC1yb2xlcy13b20iLCJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiIiLCJjbGllbnRJZCI6IndvbS1hbm9ueW1vdXMiLCJjbGllbnRIb3N0IjoiMTAuMTAuMi4xNzciLCJjbGllbnRBZGRyZXNzIjoiMTAuMTAuMi4xNzcifQ.M_0djSqJZ23minBcTjwDDWFfXC51b7NA1uWZkd0d6zbc2lwUmI8TM1sbmWwNe4tORVTlVD3SODFAj0xsNMfMLldUHp2B5Ilk4v83qRuycoSOg1JzPtS_YkStf3wzoRzfLDU1BYBDxvcLz-X6M8T5TLVQ4ZnEgJ4S_yCUnlgSXeMxBrBF7l5EuMeweA39RRsibonHbA8Mt4LCxk7aNRBSE1PpmoLi1HBF71czPoh8Jm453h4S6D8OzvU7JbCydqHk5vlYJOnqwCoqr8XzWUjqz11KdSzkLVo3yh2GCb02PN6pT0qYEsAff0Itj6c20doAtUId7oVP8Gfpl4TA5lipqw\",\"expires_in\":900,\"token_type\":\"Bearer\",\"refresh_expires_in\":0,\"scope\":\"\"}"
}
GET http://localhost:8082/api/productInventory/v2/product?serviceNumber=56711740445: {
  "Network": {
    "addresses": {
      "local": {
        "address": "::1",
        "family": "IPv6",
        "port": 2525
      },
      "remote": {
        "address": "::1",
        "family": "IPv6",
        "port": 8082
      }
    }
  },
  "Request Headers": {
    "authorization": "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJ3RVQtMEZmRGFKT1M3R2drUE53OUlWZjRvU05WSXhFbnVkYXZFbG9MSW13In0.eyJleHAiOjE2NjE3OTMxMzUsImlhdCI6MTY2MTc5MjIzNSwianRpIjoiNDU0MTQyZTctZTc2Yy00YTY3LTk1ZWQtZWNiNDAxMjAyMWUzIiwiaXNzIjoiaHR0cHM6Ly9xYS1sb2dpbi53b20uY2w6ODQ0My9hdXRoL3JlYWxtcy93b20iLCJhdWQiOiJhY2NvdW50Iiwic3ViIjoiM2JiZTI2MjMtYzQ4MS00MDMxLTk2MTItYmU0MTM3NTQyNDhmIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoid29tLWFub255bW91cyIsImFjciI6IjEiLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsiZGVmYXVsdC1yb2xlcy13b20iLCJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiIiLCJjbGllbnRJZCI6IndvbS1hbm9ueW1vdXMiLCJjbGllbnRIb3N0IjoiMTAuMTAuMi4xNzciLCJjbGllbnRBZGRyZXNzIjoiMTAuMTAuMi4xNzcifQ.M_0djSqJZ23minBcTjwDDWFfXC51b7NA1uWZkd0d6zbc2lwUmI8TM1sbmWwNe4tORVTlVD3SODFAj0xsNMfMLldUHp2B5Ilk4v83qRuycoSOg1JzPtS_YkStf3wzoRzfLDU1BYBDxvcLz-X6M8T5TLVQ4ZnEgJ4S_yCUnlgSXeMxBrBF7l5EuMeweA39RRsibonHbA8Mt4LCxk7aNRBSE1PpmoLi1HBF71czPoh8Jm453h4S6D8OzvU7JbCydqHk5vlYJOnqwCoqr8XzWUjqz11KdSzkLVo3yh2GCb02PN6pT0qYEsAff0Itj6c20doAtUId7oVP8Gfpl4TA5lipqw",
    "user-agent": "PostmanRuntime/7.29.2",
    "accept": "*/*",
    "cache-control": "no-cache",
    "postman-token": "54cb8814-e1e7-4d8e-9079-776016c7664b",
    "host": "localhost:8082",
    "accept-encoding": "gzip, deflate, br",
    "connection": "keep-alive"
  },
  "Response Headers": {
    "server-timing": "intid;desc=bdb4f26be672f52d",
    "vary": "Origin,Access-Control-Request-Method,Access-Control-Request-Headers",
    "content-type": "application/problem+json",
    "content-length": "636",
    "cache-control": "no-cache, no-store, max-age=0, must-revalidate",
    "pragma": "no-cache",
    "expires": "0",
    "x-content-type-options": "nosniff",
    "x-xss-protection": "1 ; mode=block",
    "feature-policy": "geolocation 'none'; midi 'none'; sync-xhr 'none'; microphone 'none'; camera 'none'; magnetometer 'none'; gyroscope 'none'; speaker 'none'; fullscreen 'self'; payment 'none'",
    "content-security-policy": "default-src 'self'; frame-src 'self' data:; script-src 'self' 'unsafe-inline' 'unsafe-eval' https://storage.googleapis.com; style-src 'self' 'unsafe-inline'; img-src 'self' data:; font-src 'self' data:",
    "referrer-policy": "strict-origin-when-cross-origin",
    "x-envoy-upstream-service-time": "435",
    "date": "Mon, 29 Aug 2022 16:57:21 GMT",
    "server": "envoy"
  },
  "Response Body": "{\"reason\":\"backend error:[SOAP] An error occurred while attempting to call service: http://mtls-nginx.mtls-nginx-staging.svc.cluster.local/womv/ws/cliente/v1.0/ecommerce/datos/obtener, HTTP Status Code 403, error content: Authentication failed\",\"code\":\"C0005\",\"type\":\"https://www.jhipster.tech/problem/problem-with-message\",\"title\":\"C0005\",\"status\":500,\"detail\":\"backend error:[SOAP] An error occurred while attempting to call service: http://mtls-nginx.mtls-nginx-staging.svc.cluster.local/womv/ws/cliente/v1.0/ecommerce/datos/obtener, HTTP Status Code 403, error content: Authentication failed\",\"@baseType\":\"Error\",\"@type\":\"WOMError\"}"
}

## Log SSO 2
POST http://localhost:8083/api/loginManager/anonymous: {
  "Network": {
    "addresses": {
      "local": {
        "address": "::1",
        "family": "IPv6",
        "port": 1033
      },
      "remote": {
        "address": "::1",
        "family": "IPv6",
        "port": 8083
      }
    }
  },
  "Request Headers": {
    "lm-api-key": "Oozv3FzlK6rEEyAI8xDzBbE42263Zqf9zaSRdAuNFsSD",
    "origin": "https://test.wom.cl",
    "user-agent": "PostmanRuntime/7.29.2",
    "accept": "*/*",
    "cache-control": "no-cache",
    "postman-token": "8598a30f-24e6-4a20-9937-d9319ad443b3",
    "host": "localhost:8083",
    "accept-encoding": "gzip, deflate, br",
    "connection": "keep-alive",
    "content-length": "0"
  },
  "Response Headers": {
    "server-timing": "intid;desc=dc3c43a94d05c71b",
    "vary": "Origin,Access-Control-Request-Method,Access-Control-Request-Headers",
    "access-control-allow-origin": "https://test.wom.cl",
    "access-control-allow-credentials": "true",
    "amazon-trace-id": "Root=1-630d267d-544fcc1efdc7543eabba69fb; Parent=c7c1105146827337; Sampled=1",
    "content-type": "application/json",
    "content-length": "1237",
    "set-cookie": "wom_lm_at=eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJ3RVQtMEZmRGFKT1M3R2drUE53OUlWZjRvU05WSXhFbnVkYXZFbG9MSW13In0.eyJleHAiOjE2NjE4MDcxMDUsImlhdCI6MTY2MTgwNjIwNSwianRpIjoiMDc3YzIyMjctMmNmZi00OTcwLWEwM2YtYmFjODYzMjhlODcyIiwiaXNzIjoiaHR0cHM6Ly9xYS1sb2dpbi53b20uY2w6ODQ0My9hdXRoL3JlYWxtcy93b20iLCJhdWQiOiJhY2NvdW50Iiwic3ViIjoiM2JiZTI2MjMtYzQ4MS00MDMxLTk2MTItYmU0MTM3NTQyNDhmIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoid29tLWFub255bW91cyIsImFjciI6IjEiLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsiZGVmYXVsdC1yb2xlcy13b20iLCJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiIiLCJjbGllbnRJZCI6IndvbS1hbm9ueW1vdXMiLCJjbGllbnRIb3N0IjoiMTAuMTAuMi4xNzciLCJjbGllbnRBZGRyZXNzIjoiMTAuMTAuMi4xNzcifQ.gZHhgfasDnFGoBpVteUtqCjG5oDkyKJzhf7eEtGN9PVDrxHUHpFsWqmAJZ5Zg2RZJ004PaNQDViB9mV0D4ouOCttRGoX9JX6KnDHGR4wVfvmNsOJB3OYxQ3g6MPPeu4OUSkxe5Hdz5k8sa7YYWT-v4riCY6rnZkWSL893UJk_4g08IbYZcpr3KALnIkMQwXz1ngllI-3Sdu0dpIaCfwPl0i6XgdSjc3P__nkoMWNj6N0MGcqJdGJw12ochh0YzfdxRFnb4L6-5BlQ7f89c1hPTzw3FyJwWnztiC5rbySZHZ7HE2IzSnmVXoAcv-LAhqh66TooUkfDvr4UWgRBWDO3w; Path=/; Domain=wom.cl; Max-Age=900; Expires=Mon, 29 Aug 2022 21:05:05 GMT; Secure; SameSite=None",
    "x-envoy-upstream-service-time": "35",
    "date": "Mon, 29 Aug 2022 20:50:04 GMT",
    "server": "envoy"
  },
  "Response Body": "{\"access_token\":\"eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJ3RVQtMEZmRGFKT1M3R2drUE53OUlWZjRvU05WSXhFbnVkYXZFbG9MSW13In0.eyJleHAiOjE2NjE4MDcxMDUsImlhdCI6MTY2MTgwNjIwNSwianRpIjoiMDc3YzIyMjctMmNmZi00OTcwLWEwM2YtYmFjODYzMjhlODcyIiwiaXNzIjoiaHR0cHM6Ly9xYS1sb2dpbi53b20uY2w6ODQ0My9hdXRoL3JlYWxtcy93b20iLCJhdWQiOiJhY2NvdW50Iiwic3ViIjoiM2JiZTI2MjMtYzQ4MS00MDMxLTk2MTItYmU0MTM3NTQyNDhmIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoid29tLWFub255bW91cyIsImFjciI6IjEiLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsiZGVmYXVsdC1yb2xlcy13b20iLCJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiIiLCJjbGllbnRJZCI6IndvbS1hbm9ueW1vdXMiLCJjbGllbnRIb3N0IjoiMTAuMTAuMi4xNzciLCJjbGllbnRBZGRyZXNzIjoiMTAuMTAuMi4xNzcifQ.gZHhgfasDnFGoBpVteUtqCjG5oDkyKJzhf7eEtGN9PVDrxHUHpFsWqmAJZ5Zg2RZJ004PaNQDViB9mV0D4ouOCttRGoX9JX6KnDHGR4wVfvmNsOJB3OYxQ3g6MPPeu4OUSkxe5Hdz5k8sa7YYWT-v4riCY6rnZkWSL893UJk_4g08IbYZcpr3KALnIkMQwXz1ngllI-3Sdu0dpIaCfwPl0i6XgdSjc3P__nkoMWNj6N0MGcqJdGJw12ochh0YzfdxRFnb4L6-5BlQ7f89c1hPTzw3FyJwWnztiC5rbySZHZ7HE2IzSnmVXoAcv-LAhqh66TooUkfDvr4UWgRBWDO3w\",\"expires_in\":900,\"token_type\":\"Bearer\",\"refresh_expires_in\":0,\"scope\":\"\"}"
}
GET http://localhost:8082/api/productInventory/v2/product?serviceNumber=56711740445: {
  "Network": {
    "addresses": {
      "local": {
        "address": "::1",
        "family": "IPv6",
        "port": 1038
      },
      "remote": {
        "address": "::1",
        "family": "IPv6",
        "port": 8082
      }
    }
  },
  "Request Headers": {
    "authorization": "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJ3RVQtMEZmRGFKT1M3R2drUE53OUlWZjRvU05WSXhFbnVkYXZFbG9MSW13In0.eyJleHAiOjE2NjE4MDcxMDUsImlhdCI6MTY2MTgwNjIwNSwianRpIjoiMDc3YzIyMjctMmNmZi00OTcwLWEwM2YtYmFjODYzMjhlODcyIiwiaXNzIjoiaHR0cHM6Ly9xYS1sb2dpbi53b20uY2w6ODQ0My9hdXRoL3JlYWxtcy93b20iLCJhdWQiOiJhY2NvdW50Iiwic3ViIjoiM2JiZTI2MjMtYzQ4MS00MDMxLTk2MTItYmU0MTM3NTQyNDhmIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoid29tLWFub255bW91cyIsImFjciI6IjEiLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsiZGVmYXVsdC1yb2xlcy13b20iLCJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiIiLCJjbGllbnRJZCI6IndvbS1hbm9ueW1vdXMiLCJjbGllbnRIb3N0IjoiMTAuMTAuMi4xNzciLCJjbGllbnRBZGRyZXNzIjoiMTAuMTAuMi4xNzcifQ.gZHhgfasDnFGoBpVteUtqCjG5oDkyKJzhf7eEtGN9PVDrxHUHpFsWqmAJZ5Zg2RZJ004PaNQDViB9mV0D4ouOCttRGoX9JX6KnDHGR4wVfvmNsOJB3OYxQ3g6MPPeu4OUSkxe5Hdz5k8sa7YYWT-v4riCY6rnZkWSL893UJk_4g08IbYZcpr3KALnIkMQwXz1ngllI-3Sdu0dpIaCfwPl0i6XgdSjc3P__nkoMWNj6N0MGcqJdGJw12ochh0YzfdxRFnb4L6-5BlQ7f89c1hPTzw3FyJwWnztiC5rbySZHZ7HE2IzSnmVXoAcv-LAhqh66TooUkfDvr4UWgRBWDO3w",
    "user-agent": "PostmanRuntime/7.29.2",
    "accept": "*/*",
    "cache-control": "no-cache",
    "postman-token": "96845c3b-9cf8-42e7-affe-3fe01f894c91",
    "host": "localhost:8082",
    "accept-encoding": "gzip, deflate, br",
    "connection": "keep-alive"
  },
  "Response Headers": {
    "server-timing": "intid;desc=7b543f477e395c9d",
    "vary": "Origin,Access-Control-Request-Method,Access-Control-Request-Headers",
    "content-type": "application/problem+json",
    "content-length": "636",
    "cache-control": "no-cache, no-store, max-age=0, must-revalidate",
    "pragma": "no-cache",
    "expires": "0",
    "x-content-type-options": "nosniff",
    "x-xss-protection": "1 ; mode=block",
    "feature-policy": "geolocation 'none'; midi 'none'; sync-xhr 'none'; microphone 'none'; camera 'none'; magnetometer 'none'; gyroscope 'none'; speaker 'none'; fullscreen 'self'; payment 'none'",
    "content-security-policy": "default-src 'self'; frame-src 'self' data:; script-src 'self' 'unsafe-inline' 'unsafe-eval' https://storage.googleapis.com; style-src 'self' 'unsafe-inline'; img-src 'self' data:; font-src 'self' data:",
    "referrer-policy": "strict-origin-when-cross-origin",
    "x-envoy-upstream-service-time": "437",
    "date": "Mon, 29 Aug 2022 20:50:24 GMT",
    "server": "envoy"
  },
  "Response Body": "{\"reason\":\"backend error:[SOAP] An error occurred while attempting to call service: http://mtls-nginx.mtls-nginx-staging.svc.cluster.local/womv/ws/cliente/v1.0/ecommerce/datos/obtener, HTTP Status Code 403, error content: Authentication failed\",\"code\":\"C0005\",\"type\":\"https://www.jhipster.tech/problem/problem-with-message\",\"title\":\"C0005\",\"status\":500,\"detail\":\"backend error:[SOAP] An error occurred while attempting to call service: http://mtls-nginx.mtls-nginx-staging.svc.cluster.local/womv/ws/cliente/v1.0/ecommerce/datos/obtener, HTTP Status Code 403, error content: Authentication failed\",\"@baseType\":\"Error\",\"@type\":\"WOMError\"}"
}

GET http://localhost:8084/api/party/v2/individual?rut=254668435: {
  "Network": {
    "addresses": {
      "local": {
        "address": "::1",
        "family": "IPv6",
        "port": 1049
      },
      "remote": {
        "address": "::1",
        "family": "IPv6",
        "port": 8084
      }
    }
  },
  "Request Headers": {
    "authorization": "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJ3RVQtMEZmRGFKT1M3R2drUE53OUlWZjRvU05WSXhFbnVkYXZFbG9MSW13In0.eyJleHAiOjE2NjE4MDcxMDUsImlhdCI6MTY2MTgwNjIwNSwianRpIjoiMDc3YzIyMjctMmNmZi00OTcwLWEwM2YtYmFjODYzMjhlODcyIiwiaXNzIjoiaHR0cHM6Ly9xYS1sb2dpbi53b20uY2w6ODQ0My9hdXRoL3JlYWxtcy93b20iLCJhdWQiOiJhY2NvdW50Iiwic3ViIjoiM2JiZTI2MjMtYzQ4MS00MDMxLTk2MTItYmU0MTM3NTQyNDhmIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoid29tLWFub255bW91cyIsImFjciI6IjEiLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsiZGVmYXVsdC1yb2xlcy13b20iLCJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiIiLCJjbGllbnRJZCI6IndvbS1hbm9ueW1vdXMiLCJjbGllbnRIb3N0IjoiMTAuMTAuMi4xNzciLCJjbGllbnRBZGRyZXNzIjoiMTAuMTAuMi4xNzcifQ.gZHhgfasDnFGoBpVteUtqCjG5oDkyKJzhf7eEtGN9PVDrxHUHpFsWqmAJZ5Zg2RZJ004PaNQDViB9mV0D4ouOCttRGoX9JX6KnDHGR4wVfvmNsOJB3OYxQ3g6MPPeu4OUSkxe5Hdz5k8sa7YYWT-v4riCY6rnZkWSL893UJk_4g08IbYZcpr3KALnIkMQwXz1ngllI-3Sdu0dpIaCfwPl0i6XgdSjc3P__nkoMWNj6N0MGcqJdGJw12ochh0YzfdxRFnb4L6-5BlQ7f89c1hPTzw3FyJwWnztiC5rbySZHZ7HE2IzSnmVXoAcv-LAhqh66TooUkfDvr4UWgRBWDO3w",
    "user-agent": "PostmanRuntime/7.29.2",
    "accept": "*/*",
    "cache-control": "no-cache",
    "postman-token": "ee2ae082-e5d4-40b4-8a73-01a5a309737d",
    "host": "localhost:8084",
    "accept-encoding": "gzip, deflate, br",
    "connection": "keep-alive"
  },
  "Response Headers": {
    "server-timing": "intid;desc=6a0c0658c48b6d67",
    "content-type": "application/json;charset=utf-8",
    "cache-control": "no-cache, no-store, max-age=0, must-revalidate",
    "pragma": "no-cache",
    "expires": "0",
    "x-content-type-options": "nosniff",
    "x-xss-protection": "1 ; mode=block",
    "feature-policy": "geolocation 'none'; midi 'none'; sync-xhr 'none'; microphone 'none'; camera 'none'; magnetometer 'none'; gyroscope 'none'; speaker 'none'; fullscreen 'self'; payment 'none'",
    "content-security-policy": "default-src 'self'; frame-src 'self' data:; script-src 'self' 'unsafe-inline' 'unsafe-eval' https://storage.googleapis.com; style-src 'self' 'unsafe-inline'; img-src 'self' data:; font-src 'self' data:",
    "referrer-policy": "strict-origin-when-cross-origin",
    "content-encoding": "gzip",
    "x-envoy-upstream-service-time": "12",
    "date": "Mon, 29 Aug 2022 20:56:18 GMT",
    "server": "envoy",
    "transfer-encoding": "chunked"
  },
  "Response Body": "[{\"id\":\"60bb260b61264b35d4dbbe34\",\"href\":\"urn:wom:Party:/individual/60bb260b61264b35d4dbbe34\",\"aristocraticTitle\":null,\"birthDate\":null,\"countryOfBirth\":null,\"deathDate\":null,\"familyName\":null,\"familyNamePrefix\":null,\"formattedName\":null,\"fullName\":null,\"gender\":null,\"generation\":null,\"givenName\":null,\"legalName\":null,\"location\":null,\"maritalStatus\":null,\"middleName\":null,\"nationality\":null,\"placeOfBirth\":null,\"preferredGivenName\":null,\"title\":null,\"contactMedium\":[{\"mediumType\":null,\"preferred\":null,\"characteristic\":{\"city\":\"Santiago\",\"contactType\":null,\"country\":\"Chile\",\"emailAddress\":null,\"faxNumber\":null,\"phoneNumber\":null,\"postCode\":null,\"socialNetworkId\":null,\"stateOrProvince\":null,\"street1\":\"Hamlet\",\"street2\":null,\"@baseType\":\"WOMMediumCharacteristic\",\"@schemaLocation\":\"urn:wom:Party:/schema/2.0.0\",\"@type\":\"WOMCLMediumCharacteristic\",\"apartmentNumber\":null,\"buildingName\":null,\"comunaCode\":null,\"comunaName\":null,\"latitude\":null,\"longitude\":null,\"regionCode\":null,\"regionName\":null,\"stateOrProvinceCode\":null,\"streetNumber\":\"4340\"},\"validFor\":null,\"@baseType\":null,\"@schemaLocation\":\"urn:wom:Party:/schema/2.0.0\",\"@type\":\"ContactMedium\"}],\"creditRating\":null,\"disability\":null,\"externalReference\":null,\"individualIdentification\":[{\"identificationId\":\"254668435\",\"identificationType\":\"RUT\",\"issuingAuthority\":null,\"issuingDate\":null,\"attachment\":null,\"validFor\":{\"endDateTime\":\"2021-06-06T03:21:47.844-04:00\",\"startDateTime\":\"2021-06-05T03:21:47.844-04:00\",\"@baseType\":null,\"@schemaLocation\":null,\"@type\":null},\"@baseType\":\"WOMIndividualIdentification\",\"@schemaLocation\":\"urn:wom:Party:/schema/2.0.0\",\"@type\":\"WOMCLIndividualIdentification\",\"identificationFlag\":\"true\",\"hashedSerialNumber\":null,\"audit\":\"FGAN-W6FZ-YY64-5BAI\"}],\"languageAbility\":null,\"otherName\":null,\"partyCharacteristic\":null,\"relatedParty\":null,\"skill\":null,\"status\":\"initialized\",\"taxExemptionCertificate\":null,\"@baseType\":\"WOMIndividual\",\"@schemaLocation\":\"urn:wom:Party:/schema/2.0.0\",\"@type\":\"WOMCLRegisteredIndividual\",\"createDate\":\"2021-06-05T03:21:46.988-04:00\",\"lastUpdateDate\":\"2021-06-05T03:21:47.845-04:00\",\"tenant\":{\"id\":\"1\",\"href\":\"urn:wom:Tenant:/tenant/1\",\"name\":\"WOMCL\",\"@baseType\":null,\"@schemaLocation\":\"urn:wom:Party:/schema/2.0.0\",\"@type\":\"WOMTenantRefOrValue\"},\"idpProfileId\":null,\"idpIssuer\":null}]"
}