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
      environment = "dev"
      project     = "remove-01"
    }
  }
}

# aws_instance.pivot:
resource "aws_instance" "pivot_instance" {
    tags                                 = {"Name" = "remove01pivot"}
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
    cidr_blocks = ["186.104.185.166/32"] #replace it with your ip address
  }

  #Outgoing traffic
  egress {
    from_port = 0
    protocol = "-1"
    to_port = 0
    cidr_blocks = ["0.0.0.0/0"]
  }
}
