#!/bin/bash

export AWS_ACCESS_KEY_ID="AKIATV2KEPWAAKHQDIIX"
export AWS_SECRET_ACCESS_KEY="OLlsF7fQLN4K5QloaiZm3qLUq7D6w6vvfOQmu643"
export AWS_DEFAULT_REGION="us-east-1"

# Obtain your public IP
MY_PUBLIC_IP=$(curl -s http://checkip.amazonaws.com)
echo "adding ${MY_PUBLIC_IP} to SG"
# List all security groups
# Add SSH rule for your public IP to the security groups
SECURITY_GROUP_IDS=$(aws ec2 describe-security-groups --query 'SecurityGroups[?!(contains(GroupName, `db`))].GroupId' --output text)
for sg_id in $SECURITY_GROUP_IDS; do
  aws ec2 authorize-security-group-ingress --group-id "$sg_id" --protocol tcp --port 22 --cidr "$MY_PUBLIC_IP/32"
done

SECURITY_GROUP_IDS=$(aws ec2 describe-security-groups --query 'SecurityGroups[].GroupId' --output text --filters "Name=group-name,Values=*db")
for sg_id in $SECURITY_GROUP_IDS; do
  aws ec2 authorize-security-group-ingress --group-id "$sg_id" --protocol tcp --port 5432 --cidr "$MY_PUBLIC_IP/32"
done