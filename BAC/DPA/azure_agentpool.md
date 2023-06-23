## Instalacion agente Azure DevOps
 $mkdir myagent && cd myagent
 $curl https://vstsagentpackage.azureedge.net/agent/3.220.1/vsts-agent-linux-x64-3.220.1.tar.gz --out vsts-agent-linux-x64-3.220.1.tar.gz
 $tar zxf vsts-agent-linux-x64-3.220.1.tar.gz
 $./config.sh --unattended --url https://dev.azure.com/ORG-CRI-GTI --auth 'PAT' --token '277wptq4c5y2dtcuai6u3pbaurxitqdgzmtxckzf6scn6gi74y7q' --pool 'lnxprd' 
 $sudo ./svc.sh install azbacadmin
 $sudo ./svc.sh start

## Instalacion AWX para Ansible
 $sudo dnf update -y
 $sudo dnf config-manager --add-repo https://releases.ansible.com/ansible-tower/cli/ansible-tower-cli-el8.repo -y
 $sudo dnf install ansible-tower-cli -y
 $awx --version

## Instalación PowerSheel para Linux
 $curl https://packages.microsoft.com/config/rhel/8/prod.repo | sudo tee /etc/yum.repos.d/microsoft.repo
 $sudo dnf install powershell -y
 $pwsh --version

## Instalación de Git para Linux
 $sudo dnf install git -y
 $git --version

