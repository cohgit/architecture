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
print(df["attributes"]["reportName"])

print(len(df["factMap"]["T!T"]["rows"]))

#for item in df["factMap"]["T!T"]["rows"]:
#    print('---------------------')
#    for cell in item["dataCells"]:
#        if isinstance(cell["value"], OrderedDict):
#            print(cell["label"],cell["value"]["amount"], cell["value"]["currency"] )
#        else :
#            print(cell["label"],cell["value"] )

