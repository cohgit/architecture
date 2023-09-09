terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 4.16"
    }
  }

  required_version = ">= 1.2.0"
}

provider "aws" {
  access_key = var.dev_access_key
  secret_key = var.dev_secret_key
  region  = var.dev_region

  default_tags {
    tags = {
      project     = "remove-01"
    }
  }
}
## Accesos IP Publica

variable "cidr_blocks" {
    default = [
        # Cesar Ogalde
        "186.104.185.166/32",
        "45.232.32.201/32",

        # Eduardo Leal
        "190.82.204.22/32",

        #ImageMaker
        "186.10.244.222/32"

    ] #replace it with your ip address
}

# Instance Dev
resource "aws_instance" "pivot_instance" {
    tags                                 = {"Name" = "remove01pivot","environment" = "dev"}
    associate_public_ip_address          = true
    ami                                  = "ami-0715c1897453cabd1"
    instance_type                        = "t2.small"
    key_name                             = var.dev_pivot_keyname
    subnet_id                            = var.dev_subnet_id
    vpc_security_group_ids               = [aws_security_group.pivot_sg.id]
    depends_on = [ aws_security_group.pivot_sg ]
}


resource "aws_security_group" "pivot_sg" {
  name = "removepivot_sg"
  vpc_id      = var.dev_vpc_id
  
  #Incoming traffic
  ingress {
    from_port = 22
    to_port = 22
    protocol = "tcp"
    cidr_blocks = var.cidr_blocks
  }

  #Outgoing traffic
  egress {
    from_port = 0
    protocol = "-1"
    to_port = 0
    cidr_blocks = ["0.0.0.0/0"]
  }
  tags = {"Name" = "removepivot_sg","environment" = "dev"}
}

# Instance PRD
resource "aws_instance" "pivot_instance_prd" {
    tags                                 = {"Name" = "remove01pivotprd","environment" = "prd"}
    associate_public_ip_address          = true
    ami                                  = "ami-0715c1897453cabd1"
    instance_type                        = "t2.small"
    key_name                             = var.dev_pivot_keyname
    subnet_id                            = var.prd_subnet_id
    vpc_security_group_ids               = [aws_security_group.pivot_sg_prd.id]
    depends_on = [ aws_security_group.pivot_sg_prd ]
}

resource "aws_security_group" "pivot_sg_prd" {
  name = "removepivotprd_sg"
  vpc_id      = var.prd_vpc_id
  
  #Incoming traffic
  ingress {
    from_port = 22
    to_port = 22
    protocol = "tcp"
    cidr_blocks = var.cidr_blocks
  }

  #Outgoing traffic
  egress {
    from_port = 0
    protocol = "-1"
    to_port = 0
    cidr_blocks = ["0.0.0.0/0"]
  }
  tags = {"Name" = "removepivotprd_sg","environment" = "prd"}
}

# aws_instance.bastionwinqa:
resource "aws_instance" "bastionwinqa" {
    ami                                  = "ami-064d05b4fe8515623"
    instance_type                        = "t3.medium"
    key_name                             = "KP-RemoveDev"
    subnet_id                            = "subnet-0c52ec97d0c632f4d"
    tags                                 = {
        "Name" = "bastion win-QA", "environment" = "dev"
    }
    vpc_security_group_ids               = ["sg-09e3ecd6cb5394023"]
}

resource "aws_instance" "bastionwinprd" {
    ami                                  = "ami-029bf1f640b75afe0"
    instance_type                        = "t3.medium"
    key_name                             = "KP-RemovePrd"
    subnet_id                            = "subnet-0db66156498d90144"
    tags                                 = {
        "Name" = "bastion-windows-PRD","environment" = "prd"
    }
    tenancy                              = "default"
    vpc_security_group_ids               = ["sg-09fc50089904548b3"]

}