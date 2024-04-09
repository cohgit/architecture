## Conexión a Servidor Gitlab

Para acceder al servidor debe usar la llave "epv2-key-gitlab" que esta asociado a la instancia "d2-8-gra11-epv2-gitlab".

El acceso público es a través de una IP Pública "141.94.173.137" que está asociada al DNS "repositorio.greenest.app".

acceso publico

```console
ssh -i epv2-key-gitlab ubuntu@repositorio.greenest.app
```

## Conexión a Servidor VPN de Acceso

Para acceder al servidor debe usar la llave "epv2-key-vpn" que está asociado a la instancia "	
d2-2-gra11-epv2-vpn".

El acceso público es a través de una IP Pública "141.95.166.176" que está asociada al DNS "¿?"

acceso publico

```console
ssh -i epv2-key-vpn ubuntu@141.95.166.176
```

acceso por vpn (falta autorizar)
```console
ssh -i epv2-key-vpn ubuntu@172.24.99.78
```
