import pandas as pd
import datetime
today = datetime.date.today()

str = "https://gitlab.com/arunprasath.cm/wom-liferay-dxp"
print(str)
if 'gitlab.com/womcorp' in str:
    print("si es wom")

print ("Ejecutado el {}".format(today.strftime("%y-%m-%d")))
df = pd.read_json("projects.json")
df.to_html("projects.html")