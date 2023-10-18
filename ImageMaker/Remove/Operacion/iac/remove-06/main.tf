terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "5.21.0"
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
      proyecto     = "remove-06"
    }
  }
}

resource "aws_s3_bucket" "s3bck" {
  bucket        = "dbs3export"
  force_destroy = true
}

resource "aws_iam_role" "iamrole" {
  name = "iamroles3bck"

  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Action = "sts:AssumeRole"
        Effect = "Allow"
        Sid    = ""
        Principal = {
          Service = "export.rds.amazonaws.com"
        }
      },
    ]
  })
}

data "aws_iam_policy_document" "iampolicydocument" {

  statement {
    sid = "ExportPolicy"
    actions = [
      "s3:PutObject*",
      "s3:ListBucket",
      "s3:GetObject*",
      "s3:DeleteObject",
      "s3:GetBucketLocation",
    ]
    resources = [
      aws_s3_bucket.s3bck.arn,
      "${aws_s3_bucket.s3bck.arn}/*",
    ]
  }
}

resource "aws_iam_policy" "iampolicy" {
  name   = "iampolicys3bck"
  policy = data.aws_iam_policy_document.iampolicydocument.json
}

resource "aws_iam_role_policy_attachment" "iamrolepolicyattachment" {
  role       = aws_iam_role.iamrole.name
  policy_arn = aws_iam_policy.iampolicy.arn
}

resource "aws_kms_key" "kmskey" {
  deletion_window_in_days = 10
}

resource "aws_rds_export_task" "rdss3export" {
  export_task_identifier = "dbs3exportv2"
  source_arn             = "arn:aws:rds:us-east-1:253021683072:snapshot:rds:prod-02-2023-10-17-23-11"
  s3_bucket_name         = aws_s3_bucket.s3bck.id
  iam_role_arn           = aws_iam_role.iamrole.arn
  kms_key_id             = aws_kms_key.kmskey.arn
  export_only            = ["database"]
  s3_prefix              = "remove/db"
}
