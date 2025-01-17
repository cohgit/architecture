
### Inicio de BDD

```console
docker run -e "ACCEPT_EULA=Y" -e "MSSQL_SA_PASSWORD=1209@BDD" -p 1433:1433 --name sql1 --hostname sql1 -d mcr.microsoft.com/mssql/server:2022-latest
```

### Reenvio de puertos
```console
docker run -d --name forwarder -p 1733:1433 ubuntu bash -c "apt-get update && apt-get install -y socat && socat TCP-LISTEN:1433,fork TCP:sql1:1433"
```

### reenvio de puertos N2
```console
$ docker pull alpine/socat
$ docker run -d --name forwarder -p 1733:1433 alpine/socat tcp-listen:1433,fork,reuseaddr tcp-connect:sql1:1433
```
