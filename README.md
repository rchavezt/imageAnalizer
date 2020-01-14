# ImageAnalyzer Backend
## Setting up
The application needs a MySql instance. Choose the flavor you want (docker, vagrant, local, reagular VM) and install it.

Run this commands on MySql:

`SET GLOBAL max_allowed_packet=1073741824;`

`SET GLOBAL event_scheduler = ON;`

```
DROP PROCEDURE IF EXISTS `delete_stls_unused`;
DROP EVENT IF EXISTS exec;

CREATE PROCEDURE delete_stls_unused()
DELETE FROM image WHERE image_type = 'stl' AND fk_medical_case IS NULL;

SELECT defined_date INTO @start FROM (SELECT DATE_FORMAT(CURDATE(), '%Y-%m-%d 00:00:00') AS defined_date) defined_table;

CREATE EVENT exec
  ON SCHEDULE EVERY 7 DAY
  STARTS @ini
  ON COMPLETION NOT PRESERVE ENABLE
DO CALL delete_stls_unused();
```

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