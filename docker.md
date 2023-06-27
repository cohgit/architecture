### Imagenes RHEL

```console
$docker search registry.access.redhat.com/ubi
```

### Run azbacadmin
```console
docker run -it --name rhel_ubi8 registry.access.redhat.com/ubi:ubi8
```


export 
aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin http://023608284248.dkr.ecr.us-east-1.amazonaws.com
docker pull 023608284248.dkr.ecr.us-east-1.amazonaws.com/ecrb2:latest
docker run -p 80:80 023608284248.dkr.ecr.us-east-1.amazonaws.com/cronicare-front:latest