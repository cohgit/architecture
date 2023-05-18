import http.client
import json
import pandas as pd ### for install dependency view https://pandas.pydata.org/docs/getting_started/index.html
import logging ## read https://realpython.com/python-logging/
import datetime
today = datetime.date.today()

logging.basicConfig(format='%(asctime)s - %(message)s', level=logging.INFO)
logging.info("init get projects from Azure DevOps")

def callAzureDevOps(url,pagination=True):
  logging.debug("calling {}".format(url))
  ## with proxy wom
  conn = http.client.HTTPSConnection("10.120.136.40",8080)
  conn.set_tunnel("womchile.visualstudio.com")

  ## without proxy wom
  #conn = http.client.HTTPSConnection("gitlab.com")
  payload = ''
  headers = {
    'Authorization': 'Basic bG5idmhyemJjZzJramU2bGpxeGljcWV4dmxua21mMnpraWk1N3l3bTN6N2M0ZHd3Y3g2YTo='
  }
  conn.request("GET", url, payload, headers)
  response = conn.getresponse()
  data = response.read()
  elements = json.loads(data.decode("utf-8"))
  logging.debug("elements counts {}".format(len(elements)))

  ## iterate if have nexte page
  ##if len(response.headers.get("X-Next-Page"))>0 and pagination:
    ##elements+=callGitlab(url+"&page="+response.headers.get("X-Next-Page"),pagination)

  return elements

builds = callAzureDevOps("/SERVICIOS-OPENSHIFT/_apis/build/builds?api-version=7.0&statusFilter=completed&maxBuildsPerDefinition=1")

logging.info("writing file builds md")
with open("builds.md", "w") as outMD:
  outMD.write("h1. Informe CI/CD Stages de los proyectos AzureDevOps de WOM\n")
  outMD.write("\n")
  outMD.write("Este informe tiene como finalidad obtener los stage de CI/CD ejecutados en cada proyecto\n")
  outMD.write("Ejecutado el {} \n\n".format(today.strftime("%y-%m-%d")))

  for idx,build in enumerate(builds['value']):
    if '\\release' in build['definition']['path']:
      outMD.write("# h3. ({}) {}\\{}  [git|https://womchile.visualstudio.com/SERVICIOS-OPENSHIFT/_git/{}]\n".format(build['repository']['name'],build['definition']['path'],build['definition']['name'],build['repository']['name']))
      outMD.write("    Last build  [{}|https://womchile.visualstudio.com/SERVICIOS-OPENSHIFT/_build/results?buildId={}&view=results] on {} as {}\n".format(build['buildNumber'],build['id'],build['repository']['name'],build['status']))

      try:
        logging.info(
          "{} {} [{}] {} ({}) {}\\{} timeline[{}] ".format(idx,build['buildNumber'],build['status'],build['repository']['id'],build['repository']['name'],build['definition']['path'],build['definition']['name'],build['_links']['timeline']['href'])
        )
        timeline = callAzureDevOps("/SERVICIOS-OPENSHIFT/_apis/build/builds/{}/Timeline?api-version=7.0".format(build['id']))
        build.update(timeline=timeline)
        outMD.write("    Records :\n")
        outMD.write("    ||Record||link||\n")
        for record in timeline['records']:
          logging.debug(record['name'])
          if 'log' in record and record['log'] is not None :
            outMD.write("    | {}  | [{}|{}] |\n".format(record['name'],record['id'],record['log']['url']))
          else:
            outMD.write("    | {}  | {} |\n".format(record['name'],record['id']))
      except:
        logging.error(
          "Error {} [{}] {} ".format(build['buildNumber'],build['status'],build['definition']['name'])
        )

logging.info("writing file json")
jsonString = json.dumps(builds)
with open("builds.json", "w") as outfile:
    outfile.write(jsonString)