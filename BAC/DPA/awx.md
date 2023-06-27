### Instalación de AWXKit (OpenSource)
```console
$sudo dnf install python3-pip -y
$sudo pip3 install awxkit
```

### Desinstalación de AWXKIT
```console
$sudo pip3 uninstall -y awxkit
$sudo dnf remove python3-pip -y
```
## Instalación Awx Ansioble Tower CLI
https://docs.ansible.com/ansible-tower/latest/html/towercli/usage.html#installation

```console
dnf config-manager --add-repo https://releases.ansible.com/ansible-tower/cli/ansible-tower-cli-el8.repo
dnf install ansible-tower-cli
```




Ejemplos
```console
awx 
    --conf.host '$(server_ansible_desarrollo)' 
    --conf.username '$(user_ansible_tower)' 
    --conf.password '$(password_ansible_tower)' 
    --conf.insecure 
    job_templates launch 'ExportarProyecto'
    
    --monitor 
    --wait 
    --extra_vars "{\"name_project\":\"${{ parameters.stgAnsibleProjectName }}\",\"user\": \"$(user_ansible_tower)\",\"password\": \"$(password_ansible_tower2)\",\"host\": \"$(server_ansible_desarrollo)\",\"list_workflow_jobs_templates\": [\"${{ parameters.stglist_workflow_jobs_templates }}\"]}"
```


```console
awx 
    --conf.host '${var.host_ansible_prod}' 
    --conf.username '${var.user_ansible_tower}' 
    --conf.password '${var.password_ansible_tower}' 
    --conf.insecure 
    notification_templates modify 2 
    
    --notification_configuration '{\"username\":\"ansible_Notification@baccredomatic.cr\",\"password\": \"\",\"host\": \"172.16.101.84\",\"recipients\": '${var.addressee}',\"sender\": \"ansible_Notification@baccredomatic.cr\",\"port\": 25,\"timeout\": 30,\"use_ssl\": false,\"use_tls\": false}'",
      "awx --conf.host '${var.host_ansible_prod}' --conf.username '${var.user_ansible_tower}' --conf.password '${var.password_ansible_tower}' --conf.insecure workflow_job_templates launch '${var.job_template}' --extra_vars \"envtype: '${var.envtype}'\" ",
      "awx --conf.host '$(var.host_ansible_prod)' --conf.username '$(var.user_ansible_tower)' --conf.password '$(var.password_ansible_tower)' --conf.insecure job_templates launch 'ExportarProyecto' --monitor --wait --extra_vars "{\"name_project\":\"${{ parameters.stgAnsibleProjectName }}\",\"user\": \"$(user_ansible_tower)\",\"password\": \"$(password_ansible_tower2)\",\"host\": \"$(server_ansible_desarrollo)\",\"list_workflow_jobs_templates\": [\"${{ parameters.stglist_workflow_jobs_templates }}\"]}"
```

=====================================================================================================================

Export Ansible Project

```console
awx --conf.host 'https://vmcridesaansap01.bacnet.corp.redbac.com/' --conf.username 'cogaldeh' --conf.password 'cogaldeh' --conf.insecure job_templates launch 'ExportarProyecto' --monitor --wait --extra_vars "{\"name_project\":\"Administracion_ansible\",\"user\": \"cogaldeh\",\"password\": \"cogaldeh\",\"host\": \"https://vmcridesaansap01.bacnet.corp.redbac.com/\",\"list_workflow_jobs_templates\": [\"\"]}"

```
Import Ansible Project STG
```console
awx -v --conf.host 'https://vmcridesaansst01.bacnet.corp.redbac.com/' --conf.username 'cogaldeh' --conf.password 'cogaldeh' --conf.insecure import < cnfgAdministracion_ansible.json
```



awx --conf.host 'https://vmcridesaansst01.bacnet.corp.redbac.com/' --conf.username 'devopsdes' --conf.password 'tf$an$1bl3' --conf.insecure job_templates launch "Ejecuta Healthcheck autorizaciones" --extra_vars "{\"select\":\"Banco\",\"def_ambiente\": \"Producción\"}" --monitor --verbosity 1




sshpass -p 'Qa7A6bAlsNUZ' scp /shareDpat/cnfgHealthcheck_autorizaciones.json azbacadmin@10.128.64.113:/home/azbacadmin/myagent/_work/1/a