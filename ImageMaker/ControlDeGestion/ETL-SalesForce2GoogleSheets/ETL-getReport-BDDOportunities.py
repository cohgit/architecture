from simple_salesforce import Salesforce
import requests
import pandas as pd
from io import StringIO

sf = Salesforce(password='Imit2020', username='benjamin.villanueva@imagemaker.com', security_token='6U9QeWPgoKRdPhakQyAISmHg')
print(sf)
sf_instance = 'https://imagemakersolutions.lightning.force.com/' #Your Salesforce Instance URL
reportId = '00O6g0000070I19EAE' # add report id
report_results = sf.restful('analytics/reports/{}'.format(reportId))
#df = pd.DataFrame(report_results['records']).drop(columns='attributes')
print(report_results)

#export = '?isdtp=p1&export=1&enc=UTF-8&xf=csv'
#sfUrl = sf_instance + reportId + export
#response = requests.get(sfUrl, headers=sf.headers, cookies={'sid': sf.session_id})
#download_report = response.content.decode('utf-8')
#df1 = pd.read_csv(StringIO(download_report))
#print (df1)