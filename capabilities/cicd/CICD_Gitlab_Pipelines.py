import http.client
import json
import pandas as pd ### for install dependency view https://pandas.pydata.org/docs/getting_started/index.html
import logging ## read https://realpython.com/python-logging/
import datetime
today = datetime.date.today()

logging.basicConfig(format='%(asctime)s - %(message)s', level=logging.INFO)
logging.info("init get projects from gitlab")


def callGitlab(url,pagination=True):
  url+="&per_page=100"
  logging.debug("calling {}".format(url))
  ## with proxy wom
  conn = http.client.HTTPSConnection("10.120.136.40",8080)
  conn.set_tunnel("gitlab.com")

  ## without proxy wom
  #conn = http.client.HTTPSConnection("gitlab.com")
  payload = ''
  headers = {
    'Authorization': 'Bearer glpat-necMy9M_kyr7v6H2Fyz4'
  }
  conn.request("GET", url, payload, headers)
  response = conn.getresponse()
  data = response.read()
  elements = json.loads(data.decode("utf-8"))
  logging.debug("elements counts {}".format(len(elements)))

  ## iterate if have nexte page
  if len(response.headers.get("X-Next-Page"))>0 and pagination:
    elements+=callGitlab(url+"&page="+response.headers.get("X-Next-Page"),pagination)

  return elements

logging.info("writing file projects md")
with open("projects.md", "w") as outMD:
  outMD.write("h1. Informe CI/CD Stages de los proyectos WOM\n")
  outMD.write("\n")
  outMD.write("Este informe tiene como finalidad obtener los stage de CI/CD ejecutados en cada proyecto\n")
  outMD.write("Ejecutado el {} \n\n".format(today.strftime("%y-%m-%d")))

  projects = callGitlab("/api/v4/projects?search=wom-")
  for project in projects:
    if 'gitlab.com/womcorp' in project['http_url_to_repo']:
      logging.debug("Project[{}] ID[{}] Url[{}]".format(project['name'],project['id'],project['http_url_to_repo']))
      outMD.write("# h3. {} [git|{}]\n".format(project['name_with_namespace'],project['web_url']))
      pipelines = callGitlab("/api/v4/projects/{}/pipelines?scope=finished&status=success&order_by=updated_at".format(project['id']),False)
      if (len(pipelines)>0):
        pipeline = pipelines[0]
        logging.debug(" Last Pipeline Finished Sucess ref[{}] Id[{}] last modified[{}]".format(pipeline['ref'],pipeline['id'],pipeline['created_at']))
        outMD.write("    Last pipeline  [{}|{}] on {} as {}\n".format(pipeline['id'],pipeline['web_url'],pipeline['ref'],pipeline['status']))
        jobs = callGitlab("/api/v4/projects/{}/pipelines/{}/jobs?".format(project['id'],pipeline['id']))
        outMD.write("    Stages :\n")
        outMD.write("    ||Stage||link||\n")
        for job in jobs:
          outMD.write("    | {} ({}) | [{}|{}] |\n".format(job['stage'],job['name'],job['id'],job['web_url']))
          if job['status'] == 'failed':
            logging.debug("   Stage {}-{} duration {} failed! : {} ".format(job['stage'],job['name'],job['duration'],job['failure_reason']))
          else:
            if 'runner' in job and job['runner'] is not None :
              logging.debug("   Stage {}-{} duration {} runner {} ".format(job['stage'],job['name'],job['duration'],job['runner']['name']))    
            else:
              logging.debug("   Stage {}-{} duration {} ".format(job['stage'],job['name'],job['duration']))    
        pipeline.update(jobs=jobs)
        project.update(pipeline=pipeline)

logging.info("writing file json")
jsonString = json.dumps(projects)
with open("projects.json", "w") as outfile:
    outfile.write(jsonString)

