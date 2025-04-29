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

  
## Backend Process
[Access](#access)  
[User](#user)
[Available Roles](#available-roles)  
[Authentication Process](#authentication-process)  
[Car Process](#car-process)  
[Booking Process](#booking-process)  
[Images](#images)  
[Returning a Car (Completing a Booking)](#returning-a-car-completing-the-booking)


---
  #### Access
  - From the API endpoints, you can see who can access what.
    
    - When the API endpoint has /admin, then it can only be access by the Administrator.
    - When the API endpoint has /customer, then it can only be access by the Customer.
    - When the API endpoint has /employee, then it can only be access by the Employee.
    - When the endpoint has no /admin or /customer or /employee, then it can be accessed even without authentication.

---
  #### User
  - When editing a user (customer, employee or admin), you can edit all its properties at the same time, except for the password.
  - The password needs to be separately edited.
    - Check the enpoint provided for changing the password.
---
  #### Images
- For uploading images or any data that includes images, make sure that you are using **FormData** instead of just normal application/json.

---
  #### Available Roles
  1. **Customer**
  2. **Admin** and **Employee**
     - Admin can access all the endpoints for admin and employee. But employee can only access employee endpoints but not admin endpoints.
       - This is done to show the super admin and admin hierarchy. Admin can do everything employee can but employee can only perform bookings and transactions.

---
  #### Authentication Process
  - After logging in, the API endpoint for authentication will return a response containg a token.
    - Learn how to save this as this will be used for authentication and authorizaton when accessing API endpoints.

---
  #### Car Process
  - When adding a new car, the car images are required. Do not upload car without images.
     - During this time, you don't need to call the save images separately to upload the images as it will be handled already when the car is just to be added.
       
       **NOTE:** _Only use the /api/admin/cars/{car-id}/images when uploading images for an existing car already._
  - Cars cannot be deleted also, we just archive (set as inactive) or unarchived (set as active) it.

---
  #### Booking Process
  - The amount to be paid must be calculated within the frontend, since it will be needed when adding a new booking.
  - Before saving the booking, I provided an API endpoint to check whether the given driver's license number is currently being used in a booking. 
    - Make sure to check first if the driver's license number is being used before, saving the booking.
---
  #### Returning a Car (Completing the Booking)
  - Before returning a car, check these conditions.
  - _**NOTE: When using debit/credit card as a payment, include a card ID used.**_
    
    1. **Check if the booking is due already.**
    
       - If the car is due already, pay the penalty first.
    
         - There is an endpoint that we can call and get the penalty to be paid by the customer.
         - There is also an endpoint that we can call to pay the penalty and save the transaction.
    2. Provide a prompt wherein it will ask if the car has damages.

         - If the car is damage, pay the damages first. The amount to pay will now come from the frontend.
         - Moreover, provide car damage images.
         - 
            - There is also an endpoint that we can call to pay the car damages and save the transaction.
          
- **When all of these conditions are satisfied, then that is the only time that the car should be returned.**


