import requests
import json
from datetime import datetime, timedelta
import pytz
import csv


# Parámetros iniciales de la paginación
start_at = 0
max_results = 50  # Puedes ajustar este valor según tus necesidades
total = 1  # Inicializar con un valor que permita entrar al bucle

# Define your Jira credentials
# Se solicita a Roberto Rozas la generación de Token 
# Referencia : https://support.atlassian.com/atlassian-account/docs/manage-api-tokens-for-your-atlassian-account/
auth = ('roberto.rozas@cr.asesorexternoca.com', 'ATATT3xFfGF05PFfNVS1mzk1TJQ-MfVtQts-n6IIhqrBw2L6sMt8sxvxC9IOWVYrJt7M0la643SN7CkfYmtueqUKQAmkxiQJ3NZ24Pbwmg8lzpHb-VtC1t-AhBIBUsZDQ8rK_0QSysl_PBkNjNn8Ty4hvUaEasC0410PY1dcmWGPU4ddptWPpfg=C55B82AB')

# Los resultados de la historia
results=[]

print("Searching .. Start_at:",start_at,",max_results:",max_results,",total:",total)
# Itera las paginas
while start_at < total:
    # Consulta Obtiene las historias creadas hace 2 meses ordenadas descendente
    jql = f'project in (ET,PP,DFD,BACD,ADI,OS,WP,VISN) AND issuetype=Story  ORDER BY created DESC'
    stories_url = f'https://bac-latam.atlassian.net/rest/api/2/search?jql={jql}&startAt={start_at}&maxResults={max_results}'
    stories_response = requests.get(stories_url, auth=auth, headers={'Accept': 'application/json'})

    # Valida que la respuesta no tenga errores
    if stories_response.status_code != 200:
        print(f"Error fetching stories: {stories_response.status_code}")
        print(stories_response.text)
    else:
        stories_data = stories_response.json()
        if 'issues' not in stories_data:
            print("No 'issues' key found in the response. Here is the response data:")
            print(json.dumps(stories_data, indent=4))
        else:
            total = stories_data['total']
            print("Total:",total)
            for story in stories_data['issues']:
                fields = story['fields']
                priority =''
                if 'priority' in fields and fields['priority'] is not None and 'name' in fields['priority']:
                    priority = fields['priority']['name']
                data = {
                    'project_key':fields['project']['key'],
                    'project_name': fields['project']['name'],
                    'story_key':story['key'],
                    'status_name': fields['status']['name'],
                    'priority_name':priority,
                    'created': fields['created'],
                    'summary':fields['summary'],
                    
                }
                results.append(data)
                # Actualiza el parámetro de inicio para la siguiente iteración
            start_at+=max_results
            #break
    print("Searching .. Start_at:",start_at,",max_results:",max_results,",total:",total)

# Write results to a CSV file
filename = "bac_jira_last_3_months.csv"
with open(filename, mode='w', newline='') as file:
    writer = csv.writer(file)
    headers = ['Project Key', 'Project Name','Story Key', 'Status','Priority','Created','Summary']
    writer.writerow(headers)

    for result in results:
        row = [
            result['project_key'],
            result['project_name'],
            result['story_key'],
            result['status_name'],
            result['priority_name'],
            result['created'],
            result['summary']
        ]
        writer.writerow(row)
print(f"Data written to {filename} successfully.")