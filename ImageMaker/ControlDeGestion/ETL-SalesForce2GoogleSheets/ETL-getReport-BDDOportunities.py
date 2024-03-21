from simple_salesforce import Salesforce
import salesforce_reporting
import requests
import pandas as pd
from io import StringIO
import csv
from collections import OrderedDict

sf = Salesforce(password='Imit2020', username='benjamin.villanueva@imagemaker.com', security_token='6U9QeWPgoKRdPhakQyAISmHg')
sf_instance = 'https://imagemakersolutions.lightning.force.com/' #Your Salesforce Instance URL
reportId = '00O6g0000070I19EAE' # add report id
report = sf.restful('analytics/reports/{}'.format(reportId))
parser = salesforce_reporting.ReportParser(report)
parser.records()
df = pd.DataFrame(report)
print(f' Reporte : {df["attributes"]["reportName"]}')
print(f' Cantidad de Columnas :{len(df["reportMetadata"]["detailColumns"])}')
print(f' Cantidad de Registros  :{len(df["factMap"]["T!T"]["rows"])}')


df2 = pd.DataFrame(columns=df["reportExtendedMetadata"]["detailColumnInfo"].values())

#Rescata Lista de Columnas con más información 
#df2=[]
#for item in df["reportExtendedMetadata"]["detailColumnInfo"].values():
#    df2 (item["label"])
    #titulo = f'"{item["label"]} ({item["dataType"]}) ",' 
    #print(titulo)

# Recorre Lista de Resultados
for item in df["factMap"]["T!T"]["rows"]:
    for cell in item["dataCells"]:
        if isinstance(cell["value"], OrderedDict):
            print(f'\t dato : {cell["value"]["amount"], cell["value"]["currency"]}')
        else :
            print(f'\t dato : {cell["label"], ""}')
    break

print(df2)