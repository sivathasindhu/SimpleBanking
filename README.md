# SimpleBanking

## Project set up & execution:
  •	Please download and import as Maven project in Eclipse/IntelliJ. Kindly build the project using maven (clean install) and start the main class: com.sindhu.test.projects.banking.MainApp
  
  •	Access App UI: http://localhost:8080/banking/

  •	Swagger UI URL: http://localhost:8080/swagger-ui.html

## Data Setup for Testing
  •	During MainApp start up, it starts the HSQLDB and creates all required tables and inserts 1000 uses and transactions for few users – these can be used for testing
  
  •	All users can be seen in src/main/resources/db/sql/dml.sql
  
  •	We could use “Aaden” user with “temp” password to start with
  
  •	All users are considered as ADMIN in this demo
