# object-detection-spring

Create a file `application.properties`
```properties
spring.application.name=ObjectDetection

imagga.key=
imagga.secret=
imagga.api=https://api.imagga.com/v2/

#MongoDB configuration
spring.data.mongodb.database=${MONGODB_SCHEMA:springmongodb}
spring.data.mongodb.host=${MONGODB_HOST:localhost}
spring.data.mongodb.port=${MONGODB_PORT:27017}
```