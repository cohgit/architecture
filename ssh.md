## Sin proxy
ssh -i "cogalde.pem" ec2-user@ec2-3-88-145-108.compute-1.amazonaws.com

## Con proxy desde WSL
ssh -i "cogalde.pem" ec2-user@ec2-3-88-145-108.compute-1.amazonaws.com -o "ProxyCommand=nc -x 10.120.136.40:8080 %h %p"


ssh -i "privot-stg.pem" ec2-user@54.204.34.229

## uso de proxy con openssh en windows
ssh -i "cogalde.pem" ec2-user@ec2-3-88-145-108.compute-1.amazonaws.com -o "ProxyCommand C:\Program Files (x86)\Nmap\ncat.exe --verbose --proxy-type http --proxy 10.120.136.40:8080 %h %p" -v


## conectar a las maquinas de ec2
ssh -i "cronicare-dev-ec2-web.pem" ec2-user@ec2-52-1-14-62.compute-1.amazonaws.com
ssh -i "cronicare-dev-ec2-web.pem" ec2-user@ec2-44-211-190-127.compute-1.amazonaws.com

## Obtiene archivo desde remoto con certificado
sftp -i "cronicare-dev-ec2-web.pem" ec2-user@ec2-52-1-14-62.compute-1.amazonaws.com:/opt/app.zip

sftp -i "cronicare-dev-ec2-web.pem" ec2-user@ec2-44-211-190-127.compute-1.amazonaws.com:/opt/
put app.zip


rsync -av --exclude='.git' /Users/imagemaker/dev_p/cronicare.net/cronicare-git-ops-bck/ /Users/imagemaker/dev_p/cronicare.net/cronicare-git-ops/


Credenciales ssh
comando : ssh fergus@104.237.133.145
host: acm.opengato.cl / 
user: fergus
pass: Fergus.,3221

scp fergus@104.237.133.145:~/acm-manager-db_2024-07-07T04_00-04_00.sql .