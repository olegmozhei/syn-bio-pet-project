# Serverless java API
The project is based on spring api built using AWS API Gateway
Spring API is using postgres

## Building the project locally
Install local postgres if needed:
* [Postgres](https://aws.amazon.com/cli/)

## Deploying to AWS
mvn package  
terraform apply --var-file=vars.tfvars  
*vars.tfvars is added to gitignore for security reasons

Swagger:  
http://localhost:8080/swagger-ui/index.html
http://localhost:8080/v3/api-docs