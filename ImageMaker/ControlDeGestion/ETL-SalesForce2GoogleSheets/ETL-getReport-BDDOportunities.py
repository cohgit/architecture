from simple_salesforce import Salesforce
import salesforce_reporting
import requests
import pandas as pd
from io import StringIO
import csv
from collections import OrderedDict
import gspread
from gspread_dataframe import set_with_dataframe
from google_auth_oauthlib.flow import InstalledAppFlow
from google.auth.transport.requests import Request
import pickle
import os

sf = Salesforce(password='Imit2020', username='benjamin.villanueva@imagemaker.com', security_token='6U9QeWPgoKRdPhakQyAISmHg')
sf_instance = 'https://imagemakersolutions.lightning.force.com/' #Your Salesforce Instance URL
reportId = '00O6g0000070I19EAE' # add report id
## Url Reporte
## https://imagemakersolutions.lightning.force.com/lightning/r/Report/00O6g0000070I19EAE/view?queryScope=userFolders

# Invoco API Obtiene el reporte (https://developer.salesforce.com/docs/atlas.en-us.api_analytics.meta/api_analytics/intro_build_reports_dashboards_url.htm)
report = sf.restful('analytics/reports/{}'.format(reportId))

#Convertir en DF
## https://github.com/cghall/salesforce-reporting
parser = salesforce_reporting.ReportParser(report)
parser.records()
df = pd.DataFrame(report)

print(f' Reporte : {df["attributes"]["reportName"]}')
print(f' Cantidad de Columnas :{len(df["reportMetadata"]["detailColumns"])}')
print(f' Cantidad de Registros  :{len(df["factMap"]["T!T"]["rows"])}')
## https://developer.salesforce.com/docs/atlas.en-us.api_analytics.meta/api_analytics/sforce_analytics_rest_api_factmap_example.htm?q=factMap

# Extract column labels
column_labels = [item["label"] for item in df["reportExtendedMetadata"]["detailColumnInfo"].values()]
# Extract column labels
df2 = pd.DataFrame(column_labels)
# Create an empty list to hold row data
rows_data = []
# Iterate over data and populate rows_data
for item in df["factMap"]["T!T"]["rows"]:
    row_data = {}
    for j, cell in enumerate(item["dataCells"]):
        if isinstance(cell["value"], OrderedDict):
            row_data[column_labels[j]] = cell["value"]["amount"]
        else:
            row_data[column_labels[j]] = cell["label"]
    rows_data.append(row_data)
# Create a DataFrame from the list of dictionaries
dfnew = pd.DataFrame(rows_data)

# Si modificas estos SCOPES, elimina el archivo token.pickle.
SCOPES = ['https://www.googleapis.com/auth/spreadsheets', 'https://www.googleapis.com/auth/drive']

creds = None
# El archivo token.pickle almacena los tokens de acceso y actualización del usuario, y se crea automáticamente
# cuando se completa el flujo de autorización por primera vez.
if os.path.exists('token.pickle'):
    with open('token.pickle', 'rb') as token:
        creds = pickle.load(token)
# Si no hay credenciales (válidas) disponibles, permite que el usuario inicie sesión.
if not creds or not creds.valid:
    if creds and creds.expired and creds.refresh_token:
        creds.refresh(Request())
    else:
        flow = InstalledAppFlow.from_client_secrets_file(
            'credentials.json', SCOPES)
        creds = flow.run_local_server(port=0)
    # Guarda las credenciales para la próxima ejecución
    with open('token.pickle', 'wb') as token:
        pickle.dump(creds, token)


# Autoriza las credenciales
gc = gspread.authorize(creds)

# Reemplaza esto con tu ID de Google Sheets
sheet_id = "1HzA5ob-6hKJhuoOV8F7RAyeJnNCm0hR8wtfhVgbEcUY"

# Abre el archivo de Google Sheets por su ID
spreadsheet = gc.open_by_key(sheet_id)

# Selecciona la hoja "nubox"
worksheet = spreadsheet.worksheet('BBDD Salesforce 2')
set_with_dataframe(worksheet, dfnew)