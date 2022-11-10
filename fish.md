## functions 

### proxy config
export http_proxy=http://10.120.136.40:8080/
export https_proxy=http://10.120.136.40:8080/

## Profile default command
browser: https://d-90670efc1e.awsapps.com/start#/
select profile to connect and write en aws credentials
>sudo vi ~/.aws/credentials

set aws_dig_ecomm_backend_dev for  
[aws_dig_ecomm_backend_dev]
aws_access_key_id=..
aws_secret_access_key=..
aws_session_token=..

### List Host Zones
>list-hosted-zones

### List records from one zone
>list-record-sets Z0630847RPRELQ6TPD6M

