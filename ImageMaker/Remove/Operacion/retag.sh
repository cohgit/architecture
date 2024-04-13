#!/bin/bash

# Credenciales de acceso Cesar Ogalde
AWS_ACCESS_KEY_ID=AKIATV2KEPWAAKHQDIIX
AWS_SECRET_ACCESS_KEY=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
AWS_DEFAULT_REGION=us-east-1

MANIFEST=$(aws ecr batch-get-image --repository-name remove-preprod --image-ids imageDigest=sha256:bc560ddd46db89d017773a8f534f755a8b915b8f9618978c71be83366c8233ce --query 'images[].imageManifest' --output text)
aws ecr put-image --repository-name remove-preprod --image-tag latest --image-manifest "$MANIFEST"
aws ecr describe-images --repository-name remove-preprod
