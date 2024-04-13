#!/bin/bash

# Credenciales de acceso Cesar Ogalde
AWS_ACCESS_KEY_ID=AKIATV2KEPWAAKHQDIIX
AWS_SECRET_ACCESS_KEY=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
AWS_DEFAULT_REGION=us-east-1


# Configura la región que deseas listar
AWS_REGION="us-east-1"


# Lista de tipos de recursos que deseas listar (puedes agregar o quitar según tus necesidades)
RESOURCE_TYPES=(
  "ec2"
  "s3"
  "rds"
  # Agrega más tipos de recursos aquí
)

# Itera a través de los tipos de recursos y lista los recursos
for RESOURCE_TYPE in "${RESOURCE_TYPES[@]}"
do
  # Obtiene una lista de recursos del tipo actual en la región
  RESOURCE_LIST=$(aws ${RESOURCE_TYPE} describe-resources --query "Resources[*]" --region ${AWS_REGION})

  # Imprime la lista de recursos
  echo "Recursos de tipo ${RESOURCE_TYPE} en la región ${AWS_REGION}:"
  echo "${RESOURCE_LIST}"
  echo "-------------------------------------------"
done
