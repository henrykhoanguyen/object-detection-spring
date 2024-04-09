# object-detection-spring

# Setup
### Create a file `application.properties`
```properties
spring.application.name=ObjectDetection

imagga.credentials=<username>:<password>
imagga.api=https://api.imagga.com/v2

#MongoDB configuration
spring.datasource.url=jdbc:mysql://localhost:3306/myImages
spring.datasource.username=khoa
spring.datasource.password=password

# create and drop table, good for testing, production set to none or comment it
spring.jpa.hibernate.ddl-auto=create-drop

#logging
logging.level.org.springframework.data=debug
logging.level.=error
```

### Install Docker and MySQL image
- Install Docker Desktop [here](https://docs.docker.com/desktop/)
- Run this script to install MySQL image locally.
```
docker run --name c1 
  -p 3306:3306 
  -e MYSQL_USER=<username> 
  -e MYSQL_PASSWORD=<password> 
  -e MYSQL_ROOT_PASSWORD=<password> 
  -e MYSQL_DATABASE=myImages 
  -d mysql:8.1
```
- Run this script to run MySQL image
```
docker exec -it c1 mysql -uroot -p
```

### Run server locally
To be able to run this Spring Boot app you will need to first build it. To build and package a Spring Boot app into a single executable Jar file with a Maven, use the below command. You will need to run it from the project folder which contains the pom.xml file.

```
maven package
```
or you can also use

```
mvn clean install
```

You can also use Maven plugin to run the app. Use the below example to run your Spring Boot app with Maven plugin:

```
mvn clean spring-boot:run
```

Once the server is setup you should be able to access the REST APIs over the following base-path :

http://localhost:8080/api/

Some of the important api endpoints are as follows :
http://localhost:8080/api/images (HTTP:GET)
http://localhost:8080/api/images/{image_id} (HTTP:GET)
http://localhost:8080/api/images?objects="dog,cat" (HTTP:GET)
http://localhost:8080/api/images (HTTP:POST)

## Contributors 
[Khoa Nguyen](https://www.linkedin.com/in/henrykhoanguyen/)

## License
This project is licensed under the terms of the MIT license.