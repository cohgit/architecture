import pandas as pd
import datetime
import json

today = datetime.date.today()


print ("Ejecutado el {}".format(today.strftime("%y-%m-%d")))


# load data using Python JSON module
with open('builds.json','r') as f:
    json = json.loads(f.read())

builds_with_timeline = []
for index,build in enumerate(json['value']):
    if "timeline" in build:
        builds_with_timeline.append(build)

print("{} builds with a timeline ".format(len(builds_with_timeline)))

result = pd.json_normalize(
    builds_with_timeline,
    record_path=['timeline',['records']],
    meta=['buildNumber','status','result'],
    meta_prefix='build_'
)

names = result.groupby(['name'])['name'].count()

print(names)

names.plot.bar();
