CDN no corresponde a la aplicacción
NPM como repositorio de componentes
Layout personalizado


Aplicacción contenedora
-> MicroFroned
--> Componente Visual (NPM)


Micro Aplicación
Parcel ->


Libertad de construir sus propias cosas para disponibilizar cosas

SyleComponent / Emtion 
-> porque permiten la extensión

Microfronend con multiples framework y librerías para los kit de componentes a menos que se haga con webcomponent shadown dom (multiples librerías), colocar esvelta soluciones de diseños

Module Federation¿? vistas dentro como microfronend

Nov 2021

Un unico sabor

Premisas :
1.- Microfrontend hacer mas con menos tiempo, aumentar la reutilización, adaptar (mala práctica)
2.- ¿Como afrontar los requerimientos ? input's ¿cuales?

miwom, fibra stack similar

UX homologando una librería para tener el mismo lookandfeel

Aislar por Producto y derivar por más particular

model federation webpack



Cloud Firts
No Vendor looking





https://womchile.visualstudio.com/WOMAC
Williams Nicolás Silva Ortega17:22
https://master.novalte.corp:8443/console/project/customercareapplicationservice/overview
http://10.120.1.130/sonar/
http://10.120.1.130/sonar
Usuario: womacread
Password: wom.HmDb5w




Solicita 1 persona 4H tiempo por día. MAT / E-COMMERCE
Solicita 1 persona 0,5H tiempo por día. MAT / E-COMMERCE

Semana del 26 de Septiembre

Nestor podría entrenarnos con el código para poder partir con el acelerador.






aws s3 ls
aws s3api put-object --bucket micro-frontend-lab  --key wom-static-css/
aws s3 cp dist s3://micro-frontend-lab/wom-static-css --recursive
aws s3 sync dist s3://micro-frontend-lab/wom-static-css


Bucket Policy
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Sid": "PublicStament",
            "Effect": "Allow",
            "Principal": {
                "AWS": "*"
            },
            "Action": "s3:GetObject",
            "Resource": "arn:aws:s3:::micro-frontend-lab/*"
        }
    ]
}



Etapas 1 Subida de proyecto

Directo
1.- Crear proyecto react
2.- Hacer las task de build y subida a aws s3

Versionado
1.- Hacer un pack con versión 
2.- subir pack en ruta ó versionamiento en s3
3.- ver con cloudfront versionamiento.
