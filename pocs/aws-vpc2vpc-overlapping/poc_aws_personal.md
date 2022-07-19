
Crea la VPC A
>aws ec2 create-vpc --cidr-block "172.31.0.0/16"

Crea Subredes de VPC A
>aws ec2 create-subnet --vpc-id vpc-0922a316c1963a952 --cidr-block 172.31.0.0/20 --availability-zone us-east-1a
>aws ec2 create-subnet --vpc-id vpc-0922a316c1963a952 --cidr-block 172.31.80.0/20 --availability-zone us-east-1b

