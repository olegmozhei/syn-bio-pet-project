terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 4.0"
    }
  }
}

provider "aws" {
  region = "eu-north-1"
}



variable "DbUrl" {
  description = "Environment name"
}
variable "DbUsername" {
  description = "Environment name"
}
variable "DbPassword" {
  description = "Environment name"
}
variable "spring_profiles_active" {
  description = "Environment name"
}

locals {
  function_name               = "spring-api-pet-project"
  function_handler            = "my.service.StreamLambdaHandler::handleRequest"
  function_runtime            = "java17"
  function_timeout_in_seconds = 20

  function_source_dir = "../${path.module}/target/my-service-1.0-SNAPSHOT-lambda-package.zip"
}

resource "aws_lambda_function" "function" {
  function_name = local.function_name
  handler       = local.function_handler
  runtime       = local.function_runtime
  timeout       = local.function_timeout_in_seconds
  memory_size   = 256

  filename         = local.function_source_dir
  source_code_hash = base64sha256(filebase64(local.function_source_dir))

  role = aws_iam_role.function_role.arn

  environment {
    variables = {
      "DbUrl" = var.DbUrl,
      "DbUsername"=var.DbUsername,
      "DbPassword"=var.DbPassword,
      "spring_profiles_active"=var.spring_profiles_active
    }
  }
}

resource "aws_iam_role" "function_role" {
  name = local.function_name

  assume_role_policy = jsonencode({
    Statement = [
      {
        Action = "sts:AssumeRole"
        Effect = "Allow"
        Principal = {
          Service = "lambda.amazonaws.com"
        }
      },
    ]
  })
}