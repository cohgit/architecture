# ETL de Datos Nubox
Extración de Datos de Nubox, consiste en la extracción del resumen de ventas de las empresas en Nubox

Listado de empresas:
1. ImageMaker SA
1. ImageMaker Servicios Tecnológicos Ltrda
1. ImageMaker IT

Obtener el libro de ventas / Resumen de Venta

## Ambiente Local MAC - Docker
Archivos requeridos :
1. config.toml configuracion ambiente selenium
1. docker-compose-v3-video.yml contenedor para selenium grid
1. testCDPFGeoLocation.py python de prueba

```console
mkdir videos
docker compose -f docker-compose-v3-video.yml up
```
## Variables de Accesos
18572064-0
Imit2023

## Ejecución de Pyhton con ambiente
```console
cd ~/dev/architecture/ImageMaker/ControlDeGestion
source ~/venv/cyg/bin/activate
pip install -r SeleniumGrid/requirements.txt
python3 obtieneFacturas.py
```

## Ejcución de Obtención de Resumen de venta.



#Recorre columnas
for item in df["reportExtendedMetadata"]["detailColumnInfo"].values():
    print (item["label"])


# Recorre Lista de Resultados
for item in df["factMap"]["T!T"]["rows"]:
    for cell in item["dataCells"]:
        if isinstance(cell["value"], OrderedDict):
            print(f'\t dato : {cell["value"]["amount"], cell["value"]["currency"]}')
        else :
            print(f'\t dato : {cell["label"], ""}')
    break