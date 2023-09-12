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
      proyecto     = "remove-01"
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

resource "aws_ecs_cluster" "clusterqa" {
    name  = "ClusterQA"  
    service_connect_defaults {
        namespace = "arn:aws:servicediscovery:us-east-1:253021683072:namespace/ns-5blbksxg7jrffz27"
    }
    configuration {
      execute_command_configuration {
        logging = "DEFAULT"
      }
    }
    tags = {
        "environment" = "dev"
    }
}

# aws_ecs_cluster.produccion:
resource "aws_ecs_cluster" "produccion" {
    name               = "produccion"
    tags = {
        "environment" = "prd"
    }
    configuration {
        execute_command_configuration {
            logging = "DEFAULT"
        }
    }
    service_connect_defaults {
        namespace = "arn:aws:servicediscovery:us-east-1:253021683072:namespace/ns-gsyxx3amncpkavp4"
    }
}

# aws_cloudfront_distribution.app_remove_group:
resource "aws_cloudfront_distribution" "app_remove_group" {
    aliases                        = [
        "app.removegroup.com",
    ]
    comment                        = "Produccion"
    default_root_object            = "index.html"
    enabled                        = true
    http_version                   = "http2"
    is_ipv6_enabled                = true
    price_class                    = "PriceClass_All"
    retain_on_delete               = false
    tags = {
        "environment" = "prd"
    }
    wait_for_deployment            = true
    web_acl_id                     = "arn:aws:wafv2:us-east-1:253021683072:global/webacl/CreatedByCloudFront-d3a3523c-d1ca-4fcd-84db-ff964e8632a5/df7e29f8-2a1f-4226-a19d-5e4c65d13826"

    default_cache_behavior {
        allowed_methods        = [
            "GET",
            "HEAD",
        ]
        cache_policy_id        = "658327ea-f89d-4fab-a63d-7e88639e58f6"
        cached_methods         = [
            "GET",
            "HEAD",
        ]
        compress               = true
        default_ttl            = 0
        max_ttl                = 0
        min_ttl                = 0
        smooth_streaming       = false
        target_origin_id       = "removeapp-prod.s3.us-east-1.amazonaws.com"
        trusted_key_groups     = []
        trusted_signers        = []
        viewer_protocol_policy = "redirect-to-https"
    }

    origin {
        connection_attempts = 3
        connection_timeout  = 10
        domain_name         = "removeapp-prod.s3.us-east-1.amazonaws.com"
        origin_id           = "removeapp-prod.s3.us-east-1.amazonaws.com"
    }

    restrictions {
        geo_restriction {
            locations        = []
            restriction_type = "none"
        }
    }

    viewer_certificate {
        acm_certificate_arn            = "arn:aws:acm:us-east-1:253021683072:certificate/222f1b33-847b-4a79-ac56-2f305384b809"
        cloudfront_default_certificate = false
        minimum_protocol_version       = "TLSv1.2_2021"
        ssl_support_method             = "sni-only"
    }
}

# aws_cloudfront_distribution.qa_remove_group:
resource "aws_cloudfront_distribution" "qa_remove_group" {
    aliases                        = [
        "qa.removegroup.com",
    ]
    comment                        = "QA"
    default_root_object            = "index.html"
    enabled                        = true
    http_version                   = "http1.1"
    is_ipv6_enabled                = true
    price_class                    = "PriceClass_100"
    retain_on_delete               = false
    tags = {
        "environment" = "dev"
    }
    wait_for_deployment            = true
    default_cache_behavior {
        allowed_methods            = [
            "DELETE",
            "GET",
            "HEAD",
            "OPTIONS",
            "PATCH",
            "POST",
            "PUT",
        ]
        cache_policy_id            = "658327ea-f89d-4fab-a63d-7e88639e58f6"
        cached_methods             = [
            "GET",
            "HEAD",
            "OPTIONS",
        ]
        compress                   = true
        default_ttl                = 0
        max_ttl                    = 0
        min_ttl                    = 0
        response_headers_policy_id = "60669652-455b-4ae9-85a4-c4c02393f86c"
        smooth_streaming           = false
        target_origin_id           = "bucket-s3"
        trusted_key_groups         = []
        trusted_signers            = []
        viewer_protocol_policy     = "redirect-to-https"
    }

    logging_config {
        bucket          = "removeapp-logs.s3.amazonaws.com"
        include_cookies = true
        prefix          = "CloudFront-logs"
    }

    origin {
        connection_attempts = 3
        connection_timeout  = 10
        domain_name         = "removedevs3-0000.s3.us-east-1.amazonaws.com"
        origin_id           = "bucket-s3"
    }

    restrictions {
        geo_restriction {
            locations        = []
            restriction_type = "none"
        }
    }

    viewer_certificate {
        acm_certificate_arn            = "arn:aws:acm:us-east-1:253021683072:certificate/222f1b33-847b-4a79-ac56-2f305384b809"
        cloudfront_default_certificate = false
        minimum_protocol_version       = "TLSv1.2_2021"
        ssl_support_method             = "sni-only"
    }
}

# aws_cloudfront_distribution.removesolutions:
resource "aws_cloudfront_distribution" "removesolutions" {
    aliases                        = [
        "removesolutions.com",
    ]
    comment                        = "Prod -old"
    default_root_object            = "index.html"
    enabled                        = true
    http_version                   = "http1.1"
    is_ipv6_enabled                = true
    price_class                    = "PriceClass_100"
    retain_on_delete               = false
    tags = {
        "environment" = "dev"
    }
    wait_for_deployment            = true

    default_cache_behavior {
        allowed_methods        = [
            "DELETE",
            "GET",
            "HEAD",
            "OPTIONS",
            "PATCH",
            "POST",
            "PUT",
        ]
        cache_policy_id        = "658327ea-f89d-4fab-a63d-7e88639e58f6"
        cached_methods         = [
            "GET",
            "HEAD",
            "OPTIONS",
        ]
        compress               = true
        default_ttl            = 0
        max_ttl                = 0
        min_ttl                = 0
        smooth_streaming       = false
        target_origin_id       = "removesolutions.com"
        trusted_key_groups     = []
        trusted_signers        = []
        viewer_protocol_policy = "redirect-to-https"
    }

    logging_config {
        bucket          = "removeapp-logs.s3.amazonaws.com"
        include_cookies = true
        prefix          = "CloudFront-Prd"
    }

    origin {
        connection_attempts = 3
        connection_timeout  = 10
        domain_name         = "remove-solution-temp.s3.us-east-1.amazonaws.com"
        origin_id           = "removesolutions.com"
    }

    restrictions {
        geo_restriction {
            locations        = []
            restriction_type = "none"
        }
    }
    viewer_certificate {
        acm_certificate_arn            = "arn:aws:acm:us-east-1:253021683072:certificate/92f48d25-7bd5-41dd-8dc4-d9dd77e7a22e"
        cloudfront_default_certificate = false
        minimum_protocol_version       = "TLSv1.2_2021"
        ssl_support_method             = "sni-only"
    }
}

# aws_db_instance.db_prd:
resource "aws_db_instance" "db_prd" {
    allocated_storage                     = 200
    auto_minor_version_upgrade            = true
    availability_zone                     = "us-east-1f"
    backup_retention_period               = 30
    backup_window                         = "23:00-03:00"
    ca_cert_identifier                    = "rds-ca-2019"
    copy_tags_to_snapshot                 = true
    customer_owned_ip_enabled             = false
    db_name                               = "remove_prd"
    db_subnet_group_name                  = "default-vpc-0b54e6ccfbaa7ef81"
    delete_automated_backups              = true
    deletion_protection                   = false
    enabled_cloudwatch_logs_exports       = [
        "postgresql",
        "upgrade",
    ]
    engine                                = "postgres"
    iam_database_authentication_enabled   = false
    identifier                            = "prod-02"
    instance_class                        = "db.m5.2xlarge"
    iops                                  = 5000
    kms_key_id                            = "arn:aws:kms:us-east-1:253021683072:key/afe63c03-d033-40c2-9944-ac7477e6bffe"
    license_model                         = "postgresql-license"
    maintenance_window                    = "sat:03:00-sat:07:00"
    max_allocated_storage                 = 0
    monitoring_interval                   = 0
    multi_az                              = true
    network_type                          = "IPV4"
    option_group_name                     = "default:postgres-13"
    parameter_group_name                  = "default.postgres13"
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
    vpc_security_group_ids                = [
        "sg-03f82adbf2af87dd1",
        "sg-07fb58035f911f19f",
    ]
}

# aws_db_instance.db_qa:
resource "aws_db_instance" "db_qa" {
    allocated_storage                     = 100
    auto_minor_version_upgrade            = true
    availability_zone                     = "us-east-1c"
    backup_retention_period               = 7
    backup_window                         = "08:30-09:00"
    ca_cert_identifier                    = "rds-ca-2019"
    copy_tags_to_snapshot                 = true
    customer_owned_ip_enabled             = false
    db_subnet_group_name                  = "default-vpc-0ba0c20d6f262d95e"
    delete_automated_backups              = true
    deletion_protection                   = false
    enabled_cloudwatch_logs_exports       = []
    engine                                = "postgres"
    iam_database_authentication_enabled   = false
    identifier                            = "remove-qa"
    instance_class                        = "db.m5.xlarge"
    iops                                  = 0
    kms_key_id                            = "arn:aws:kms:us-east-1:253021683072:key/afe63c03-d033-40c2-9944-ac7477e6bffe"
    license_model                         = "postgresql-license"
    maintenance_window                    = "sun:07:57-sun:08:27"
    max_allocated_storage                 = 0
    monitoring_interval                   = 0
    multi_az                              = false
    network_type                          = "IPV4"
    option_group_name                     = "default:postgres-13"
    parameter_group_name                  = "default.postgres13"
    performance_insights_enabled          = false
    performance_insights_retention_period = 0
    port                                  = 5432
    publicly_accessible                   = true
    skip_final_snapshot                   = true
    storage_encrypted                     = true
    storage_throughput                    = 0
    storage_type                          = "gp2"
    tags = {
        "environment" = "dev"
    }
    username                              = "devadmin"
    vpc_security_group_ids                = [
        "sg-01d6d2e53d548a1bf",
        "sg-02972ab7dfd6a8c8e",
        "sg-0700dbd68348d177b",
    ]
}