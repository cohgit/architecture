import os
import requests
import xml.etree.ElementTree as ET
from requests.auth import HTTPBasicAuth
import json

# Ruta al archivo weblogic.xml
baseproyectos = "/Users/imagemaker/dev/bch"
proyecto = baseproyectos + "/cdn-venta-resolutor-api"
archivo_xml = proyecto + "/src/main/webapp/WEB-INF/weblogic.xml"
pathCopyLib = "/Users/imagemaker/dev/bch/u04/wls12130/user_projects/domains/base_domain/lib/"

# URL del repositorio Nexus
nexus_search_url = "http://nexus.bch.bancodechile.cl:8081/service/extdirect"
nexus_username = "Consulta_Nexus"
nexus_password = "En4FtYo07ule"
war_files = []


# Crear un objeto ElementTree y analizar el archivo XML
tree = ET.parse(archivo_xml)
root = tree.getroot()

# Definir el espacio de nombres utilizado en el archivo XML
namespace = {'wls': 'http://xmlns.oracle.com/weblogic/weblogic-web-app'}

# Buscar y listar los elementos wls:library-name dentro de wls:library-ref
#for weblogic_web_app in root.findall('.//wls:weblogic-web-app', namespaces=namespace):
for library_library_ref in root.findall('wls:library-ref', namespaces=namespace):
    library_name_element = library_library_ref.find('wls:library-name', namespaces=namespace)

    specification_version_element = library_library_ref.find('wls:specification-version', namespaces=namespace)
    if specification_version_element is not None and library_name_element is not None:
        library_name = library_name_element.text
        specification_version = specification_version_element.text
        #print(f'wls:library-name: {library_name}-{specification_version}')

        # Crear los datos de solicitud en el formato esperado por el servicio de búsqueda en Nexus
        payload = json.dumps({
            "action": "coreui_Search",
            "method": "read",
            "data": [
                {
                "page": 1,
                "start": 0,
                "limit": 300,
                "filter": [
                    {
                    "property": "name.raw",
                    "value": library_name
                    },
                    {
                    "property": "version",
                    "value": specification_version
                    }
                ]
                }
            ],
            "type": "rpc",
            "tid": 63
            })

        headers = {
            'Content-Type': 'application/json',
            'Authorization': 'Basic Q29uc3VsdGFfTmV4dXM6RW40RnRZbzA3dWxl'
        }

        response = requests.request("POST", nexus_search_url, headers=headers, data=payload)

        if response.status_code == 200:
            data = response.json()
            #print (data)
            
            if data.get('result') and data['result'].get('data') and data['result']['data']:
                result = data['result']['data'][0]
                version = result.get('version')
                name = result.get('name')
                repositoryName = result.get('repositoryName')
                group = result.get('group')
                if version and name and repositoryName:
                    print(f'Nexus Found: {repositoryName} {name}-{version} ')
                    group = group.replace(".", "/")
                    download_url = f'http://nexus.bch.bancodechile.cl:8081/repository/{repositoryName}/{group}/{name}/{version}/{name}-{version}.war'
                    war_files.append(download_url)
                    #print(download_url)
                else:
                    print('Version information not found')
            else:
                print('Library not found in Nexus')
        else:
            print(f'Failed to search the library in Nexus (Status Code: {response.status_code})')

# Configuration
username = "Consulta_Nexus"
password = "En4FtYo07ule"

# Download WAR files from Nexus
for war_path in war_files:
    war_download_url = f"{war_path}"
    war_file_name = pathCopyLib + os.path.basename(war_path)

    try:
        response = requests.get(war_download_url, auth=(username, password), stream=True)
        response.raise_for_status()

        with open(war_file_name, 'wb') as war_file:
            for chunk in response.iter_content(chunk_size=8192):
                war_file.write(chunk)

        print(f"Downloaded {war_file_name} from Nexus.")
    except requests.exceptions.RequestException as e:
        print(f"Failed to download the WAR file {war_file_name}: {e}")
