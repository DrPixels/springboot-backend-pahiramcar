# Pahiram Car Backend Installation Setup

## Recommended Way
1. Install IntelliJ IDEA Ultimate
2. Clone the repository.
3. Check the [steps](#steps-in-changing-the-database-setup-from-the-spring-boot) in changing the database setup.
4. After changing the settings, run the application.
   - If the application won't run due to dependencies error,
     - Go to the **pom.xml**.
     - Right click, then hover to **Maven**
     - Then choocse, **Sync Project**.
---
### Steps in changing the database setup from the Spring Boot
1. Go to **src** -> **main** -> **java** -> **resources** -> **application.yml**
2. Change the following values depending on the setup of your MySQL database.
   - Under **spring**, then **datasource**:
     - **url** *(jdbc:mysql://localhost:3306/pahiramcar?createDatabaseIfNotExist=true)*
       - **pahiramcar** refers to the database name
         - You can change the name to any name, just make sure to adjust the settings also.  
       - In here, you can include this part **?createDatabaseIfNotExist=true**, to create the database if it is not currently existing.
     - **username**
     - **password**  
    
*Also, make sure that you have downloaded the MySQL JDBC Driver.*

---
### Accessing API Documentation
After successfully running the Spring Boot application, go to this link within your browser to access the API documentation.  
- *Note: You can only access the API documentation when the server is running.*  
> **http://localhost:8080/swagger-ui/index.html#/**
---
### Accessing the API from the Frontend
* Make sure that the frontend is running either on port **8080** or **5174**.
---

