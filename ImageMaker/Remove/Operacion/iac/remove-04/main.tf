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
  access_key = var.access_key
  secret_key = var.secret_key
  region  = var.region
  default_tags {
    tags = {
      proyecto     = "remove-04"
    }
  }
}

resource "aws_vpc" "vpc" {
  cidr_block = "14.10.0.0/16"
  tags = {
    Name = "VPC Remove 04"
  }
}

resource "aws_subnet" "subnet_1" {
  vpc_id            = aws_vpc.vpc.id
  cidr_block        = "14.10.1.0/24"
  availability_zone = "sa-east-1a"

  tags = {
    Name = "Remove 04 Public Subnet 1"
  }
}
resource "aws_subnet" "subnet_2" {
  vpc_id            = aws_vpc.vpc.id
  cidr_block        = "14.10.2.0/24"
  availability_zone = "sa-east-1b"

  tags = {
    Name = "Remove 04 Public Subnet 2"
  }
}

resource "aws_internet_gateway" "ig" {
  vpc_id = aws_vpc.vpc.id
  tags = {
    Name = "Remove 04 Internet Gateway"
  }
}

resource "aws_route_table" "public_rt" {
  vpc_id = aws_vpc.vpc.id

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.ig.id
  }

  route {
    ipv6_cidr_block = "::/0"
    gateway_id      = aws_internet_gateway.ig.id
  }

  tags = {
    Name = "Public Route Table"
  }
}

resource "aws_route_table_association" "rt_1" {
  subnet_id      = aws_subnet.subnet_1.id
  route_table_id = aws_route_table.public_rt.id
}
resource "aws_route_table_association" "rt_2" {
  subnet_id      = aws_subnet.subnet_2.id
  route_table_id = aws_route_table.public_rt.id
}

resource "aws_security_group" "bdd_sg" {
  name   = "postgres"
  vpc_id = aws_vpc.vpc.id

  ingress {
    from_port   = 5432
    to_port     = 5432
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = -1
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_db_subnet_group" "postgresql_subnet_group" {
    name       = "postgresubgroup"
    subnet_ids = [
      aws_subnet.subnet_1.id,  
      aws_subnet.subnet_2.id
    ]

    tags = {
        Name = "PostgreSQL subnet group"
    }
}


resource "aws_kms_key" "prdkmskey" {
  description = "Example PRD KMS Key"
}

# aws_db_instance.db_prd:
resource "aws_db_instance" "db_prd" {
    allocated_storage                     = 200
    auto_minor_version_upgrade            = true
    backup_retention_period               = 30
    backup_window                         = "23:00-03:00"
    ca_cert_identifier                    = "rds-ca-2019"
    copy_tags_to_snapshot                 = true
    customer_owned_ip_enabled             = false
    db_name                               = "remove04prd"
    identifier                            = "remove04prd"
    kms_key_id                            = aws_kms_key.prdkmskey.arn
    db_subnet_group_name                  = aws_db_subnet_group.postgresql_subnet_group.name
    delete_automated_backups              = true
    deletion_protection                   = false
    engine                                = "postgres"
    iam_database_authentication_enabled   = false
    instance_class                        = "db.m5.2xlarge"
    iops                                  = 5000
    license_model                         = "postgresql-license"
    maintenance_window                    = "sat:03:00-sat:07:00"
    max_allocated_storage                 = 0
    monitoring_interval                   = 0
    multi_az                              = false
    network_type                          = "IPV4"
    performance_insights_enabled          = false
    performance_insights_retention_period = 0
    port                                  = 5432
    publicly_accessible                   = false
    skip_final_snapshot                   = true
    storage_encrypted                     = true
    storage_throughput                    = 0
    storage_type                          = "io1"
    tags = {
        "environment" = "prd"
    }
    username                              = "adminprd"
    password                              = "adminprd"
    vpc_security_group_ids                = [
       aws_security_group.bdd_sg.id
    ]
}