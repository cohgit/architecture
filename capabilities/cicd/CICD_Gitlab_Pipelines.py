import http.client
import json

def callGitlab(url,pagination=True):
  url+="&per_page=100"
  print("calling {}".format(url))
  ## with proxy wom
  #conn = http.client.HTTPSConnection("10.120.136.40",8080)
  #conn.set_tunnel("gitlab.com")

  ## without proxy wom
  conn = http.client.HTTPSConnection("gitlab.com")
  payload = ''
  headers = {
    'Authorization': 'Bearer glpat-necMy9M_kyr7v6H2Fyz4'
  }
  conn.request("GET", url, payload, headers)
  response = conn.getresponse()
  data = response.read()
  elements = json.loads(data.decode("utf-8"))
  ##print("elements counts {}".format(len(elements)))

  ## iterate if have nexte page
  if len(response.headers.get("X-Next-Page"))>0 and pagination:
    elements+=callGitlab(url+"&page="+response.headers.get("X-Next-Page"),pagination)

  return elements

projects = callGitlab("/api/v4/projects?search=wom-")
for project in projects:
  print("Project[{}] ID[{}] Url[{}]".format(project['name'],project['id'],project['http_url_to_repo']))
  pipelines = callGitlab("/api/v4/projects/{}/pipelines?scope=finished&status=success&order_by=updated_at".format(project['id']),False)
  if (len(pipelines)>0):
    pipeline = pipelines[0]
    print(" Last Pipeline Finished Sucess ref[{}] Id[{}] last modified[{}]".format(pipeline['ref'],pipeline['id'],pipeline['created_at']))
    jobs = callGitlab("/api/v4/projects/{}/pipelines/{}/jobs?".format(project['id'],pipeline['id']))
    for job in jobs:
      if job['status'] == 'failed':
        print("   Stage {}-{} duration {} failed! : {} ".format(job['stage'],job['name'],job['duration'],job['failure_reason']))
      else:
        if 'runner' in job and job['runner'] is not None :
          print("   Stage {}-{} duration {} runner {} ".format(job['stage'],job['name'],job['duration'],job['runner']['name']))    
        else:
          print("   Stage {}-{} duration {} ".format(job['stage'],job['name'],job['duration']))    
