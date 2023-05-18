Ejemplos
awx 
    --conf.host '$(server_ansible_desarrollo)' 
    --conf.username '$(user_ansible_tower)' 
    --conf.password '$(password_ansible_tower)' 
    --conf.insecure 
    job_templates launch 'ExportarProyecto'
    
    --monitor 
    --wait 
    --extra_vars "{\"name_project\":\"${{ parameters.stgAnsibleProjectName }}\",\"user\": \"$(user_ansible_tower)\",\"password\": \"$(password_ansible_tower2)\",\"host\": \"$(server_ansible_desarrollo)\",\"list_workflow_jobs_templates\": [\"${{ parameters.stglist_workflow_jobs_templates }}\"]}"


awx 
    --conf.host '${var.host_ansible_prod}' 
    --conf.username '${var.user_ansible_tower}' 
    --conf.password '${var.password_ansible_tower}' 
    --conf.insecure 
    notification_templates modify 2 
    
    --notification_configuration '{\"username\":\"ansible_Notification@baccredomatic.cr\",\"password\": \"\",\"host\": \"172.16.101.84\",\"recipients\": '${var.addressee}',\"sender\": \"ansible_Notification@baccredomatic.cr\",\"port\": 25,\"timeout\": 30,\"use_ssl\": false,\"use_tls\": false}'",
      "awx --conf.host '${var.host_ansible_prod}' --conf.username '${var.user_ansible_tower}' --conf.password '${var.password_ansible_tower}' --conf.insecure workflow_job_templates launch '${var.job_template}' --extra_vars \"envtype: '${var.envtype}'\" ",
      "awx --conf.host '$(var.host_ansible_prod)' --conf.username '$(var.user_ansible_tower)' --conf.password '$(var.password_ansible_tower)' --conf.insecure job_templates launch 'ExportarProyecto' --monitor --wait --extra_vars "{\"name_project\":\"${{ parameters.stgAnsibleProjectName }}\",\"user\": \"$(user_ansible_tower)\",\"password\": \"$(password_ansible_tower2)\",\"host\": \"$(server_ansible_desarrollo)\",\"list_workflow_jobs_templates\": [\"${{ parameters.stglist_workflow_jobs_templates }}\"]}"

=====================================================================================================================

Export Ansible Project
awx --conf.host 'https://vmcridesaansap01.bacnet.corp.redbac.com/' --conf.username 'cogaldeh' --conf.password 'cogaldeh' --conf.insecure job_templates launch 'ExportarProyecto' --monitor --wait --extra_vars "{\"name_project\":\"Administracion_ansible\",\"user\": \"cogaldeh\",\"password\": \"cogaldeh\",\"host\": \"https://vmcridesaansap01.bacnet.corp.redbac.com/\",\"list_workflow_jobs_templates\": [\"\"]}" > 


Import Ansible Project
awx -v --conf.host '$(server_ansible_staging)' --conf.username '$(user_ansible_tower)' --conf.password '$(password_ansible_tower)' --conf.insecure import < cnfg${{ parameters.stgAnsibleProjectName }}.json