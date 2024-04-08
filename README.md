# object-detection-spring

Create a file `application.properties`
```properties
spring.application.name=ObjectDetection

imagga.credentials=<username>:<password>
imagga.api=https://api.imagga.com/v2/tags

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