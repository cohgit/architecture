## Instala Cliente AWS 
Usa la siguiente referencia para ubuntu
https://docs.aws.amazon.com/cli/latest/userguide/getting-started-install.html

## configura redenciales 
Credenciales de César Ogalde en AWS

access_key="AKIATV2KEPWAAKHQDIIX"
secret_key="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"

```console
aws configure set aws_access_key_id AKIATV2KEPWAAKHQDIIX
aws configure set aws_secret_access_key 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
aws configure set default.region us-est-1
```

Prueba la configuración obteniendo la info del usuario

```console
aws sts get-caller-identity
{
    "UserId": "AIDATV2KEPWAAD6TIXQWG",
    "Account": "253021683072",
    "Arn": "arn:aws:iam::253021683072:user/cesar.ogalde"
}
```

## configuración de terraform  

La referencia de instalación  está en 
https://developer.hashicorp.com/terraform/tutorials/aws-get-started/install-cli

```console
$ terraform --version
Terraform v1.8.1
on linux_amd64
```