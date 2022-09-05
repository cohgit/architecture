

## Sin proxy
ssh -i "cogalde.pem" ec2-user@ec2-3-88-145-108.compute-1.amazonaws.com

## Con proxy desde WSL
ssh -i "cogalde.pem" ec2-user@ec2-3-88-145-108.compute-1.amazonaws.com -o "ProxyCommand=nc -x 10.120.136.40:8080 %h %p"


ssh -i "privot-stg.pem" ec2-user@54.204.34.229

## uso de proxy con openssh en windows
ssh -i "cogalde.pem" ec2-user@ec2-3-88-145-108.compute-1.amazonaws.com -o "ProxyCommand C:\Program Files (x86)\Nmap\ncat.exe --verbose --proxy-type http --proxy 10.120.136.40:8080 %h %p" -v


