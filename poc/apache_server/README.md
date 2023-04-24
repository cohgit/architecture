Esta es una POC para revisar las variables

## Uso de Aimgen Docker con Variable

## Creación de Azure Container Instance con Imagen

Autenticación de Azure 
az login
[
  {
    "cloudName": "AzureCloud",
    "homeTenantId": "ca17d960-f14a-4694-93c3-4dec0a12781a",
    "id": "702cf96d-4910-446c-b26a-002ca67c2505",
    "isDefault": true,
    "managedByTenants": [
      {
        "tenantId": "ebad9241-2387-4155-8952-c8d8718bf80c"
      }
    ],
    "name": "Hügga",
    "state": "Enabled",
    "tenantId": "ca17d960-f14a-4694-93c3-4dec0a12781a",
    "user": {
      "name": "cesar.ogalde@huggaim.onmicrosoft.com",
      "type": "user"
    }
  }
]

az group create -l westus -n poc_arquitectura_cesarogalde
{
  "id": "/subscriptions/702cf96d-4910-446c-b26a-002ca67c2505/resourceGroups/poc_arquitectura_cesarogalde",
  "location": "westus",
  "managedBy": null,
  "name": "poc_arquitectura_cesarogalde",
  "properties": {
    "provisioningState": "Succeeded"
  },
  "tags": null,
  "type": "Microsoft.Resources/resourceGroups"
}

az ad sp create-for-rbac --role Contributor -n spapachewebserver --scopes /subscriptions/702cf96d-4910-446c-b26a-002ca67c2505/resourceGroups/poc_arquitectura_cesarogalde
Creating 'Contributor' role assignment under scope '/subscriptions/702cf96d-4910-446c-b26a-002ca67c2505/resourceGroups/poc_arquitectura_cesarogalde'
The output includes credentials that you must protect. Be sure that you do not include these credentials in your code or check the credentials into your source control. For more information, see https://aka.ms/azadsp-cli
{
  "appId": "bd0b0214-eedc-4bbc-9a8b-e1943ff0c0af",
  "displayName": "spapachewebserver",
  "password": "d1.8Q~69c96c2yxMEwILH9fQQcyKVM-KHUvwMbFr",
  "tenant": "ca17d960-f14a-4694-93c3-4dec0a12781a"
}


## LLamada desde Postman