# ImageAnalyzer Backend
## Setting up
The application needs a MySql instance. Choose the flavor you want (docker, vagrant, local, reagular VM) and install it.

Run this command on MySql:

`SET GLOBAL max_allowed_packet=1073741824;`

Once you have MySql Server running, update the values as needed on the application.properties file:

```
spring.jpa.hibernate.ddl-auto=none #Use this property to create the DB (create for creation and none to use the one existing)
spring.datasource.url=jdbc:mysql://localhost:3306/imageAnalizer
spring.datasource.username=user
spring.datasource.password=password
```
## Compilation and deployment
The backend is managed by Maven, the goal to compile and deploy is:

`mvn spring-boot:run`

Hibernate will create the DB and tomcat will deploy the application


## Swagger
After the application runs, swagger should be available on:

`http://localhost:9090/imageAnalizer/swagger-ui.html`