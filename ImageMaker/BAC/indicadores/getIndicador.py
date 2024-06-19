import requests
import json
from datetime import datetime, timedelta
import pytz
import csv


# Define your Jira credentials
# Se solicita a Roberto Rozas la generación de Token 
# Referencia : https://support.atlassian.com/atlassian-account/docs/manage-api-tokens-for-your-atlassian-account/
auth = ('roberto.rozas@cr.asesorexternoca.com', 'ATATT3xFfGF05PFfNVS1mzk1TJQ-MfVtQts-n6IIhqrBw2L6sMt8sxvxC9IOWVYrJt7M0la643SN7CkfYmtueqUKQAmkxiQJ3NZ24Pbwmg8lzpHb-VtC1t-AhBIBUsZDQ8rK_0QSysl_PBkNjNn8Ty4hvUaEasC0410PY1dcmWGPU4ddptWPpfg=C55B82AB')

# Consulta Obtiene las historias creadas hace 2 meses ordenadas descendente
jql = f'project=EDPEH AND issuetype=Story AND created > startOfMonth(-2) ORDER BY created DESC'
stories_url = f'https://bac-latam.atlassian.net/rest/api/2/search?jql={jql}'
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
        #Consulta las pulicaciones y release y entregas 
        versions_url = 'https://bac-latam.atlassian.net/rest/api/2/project/EDPEH/versions'
        versions_response = requests.get(versions_url, auth=auth, headers={'Accept': 'application/json'})

        if versions_response.status_code != 200:
            print(f"Error fetching versions: {versions_response.status_code}")
            print(versions_response.text)
        else:
            versions_data = versions_response.json()

            # Process data
            results = []

            for story in stories_data['issues']:
                story_id = story['id']
                story_key = story['key']
                story_summary = story['fields']['summary']
                story_creator = story['fields']['creator']['emailAddress']
                story_priority = story['fields']['priority']['name']
                story_creation_date = datetime.strptime(story['fields']['created'], '%Y-%m-%dT%H:%M:%S.%f%z')

                for version in versions_data:
                    if version['released']:
                        release_date = datetime.strptime(version['releaseDate'], '%Y-%m-%d')
                        
                        # Convert release_date to the same timezone as story_creation_date
                        release_date = release_date.replace(tzinfo=pytz.UTC)
                        
                        # Calculate the time difference in days
                        time_difference = (release_date - story_creation_date).days
                        
                        results.append({
                            'story_id': story_id,
                            'story_key': story_key,
                            'story_summary': story_summary,
                            'story_priority':story_priority,
                            'story_creation_date':story_creation_date,
                            'story_creator':story_creator,
                            'version_id': version['id'],
                            'version_name': version['name'],
                            'release_date':release_date,
                            'time_to_release': time_difference
                        })

            # Write results to a CSV file
            with open('bac_cero_papel_last_3_months.csv', mode='w', newline='') as file:
                writer = csv.writer(file)
                headers = ['Story ID', 'Story Key', 'Story Summary','Story Priority','Creator','Story Creation Date', 'Version ID', 'Version Name','Version Realease Date', 'Time to Release (days)']
                writer.writerow(headers)

                for result in results:
                    row = [
                        result['story_id'],
                        result['story_key'],
                        result['story_summary'],
                        result['story_priority'],
                        result['story_creator'],
                        result['story_creation_date'],
                        result['version_id'],
                        result['version_name'],
                        result['release_date'],
                        result['time_to_release']
                    ]
                    writer.writerow(row)

            print("Data written to bac_cero_papel_last_3_months.csv successfully.")
        