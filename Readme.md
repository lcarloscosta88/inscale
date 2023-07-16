
## Technologies Used (in `pom.xml`)

* JDK 20
* Java 17
* Maven
* SpringBoot 3.1.1
* JPA
* Lombok
* H2 Database
* OpenApi 2.1.0
* Aws 1.12.506
* Docker
* RESTful
* Junit5
* Mockito

## Project Structure

* Aws package - Contains the AWS configuration and services for S3 and SQS
* Controller package - Rest controller for our CRUD and Get methods
* DTO package - Class with two DTO - PersonDTO and AddressDTO
* Entity package - Class with our entities Person and Address, one person can have multiple address
* Repository package - Class with the two repositories
* Services package - Class with our services
* Exception package - Custom exception for person


## Configuring AWS Services SQS/S3

execute
docker-compose -f .\docker-compose-services.yml up -d --build

Install AWS CLI - https://aws.amazon.com/pt/cli/

check configurations "aws configure list", it must be the same defined in the docker-compose-services.yml, if not do a "aws connfigure" and type the configurations

after that, create the bucket
aws --endpoint-url=http://localhost:4566 s3 mb s3://mybucket

To create the SQS queue
aws --endpoint-url=http://localhost:4566 sqs create-queue --queue-name myqueue

## Configuring H2 Database

Must download first - http://h2database.com/html/download.html
After the app is running, access http://localhost:8082/

## Running Docker
docker image build -t test .
after downloading all images
docker container run -- test -p 8012:8012 -d test 
