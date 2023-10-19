<h1 align="center" > Integrative project: Messenger service Rest API</h1> 
<p align="right">
   <img src="https://img.shields.io/badge/STATUS-DEVELOPING-green">
</p>

<details>
  <summary>Table of Contents</summary>
  <ul>
    <li><a href="#project-overview">Project overview</a></li>
    <li><a href="#data-modeling">Data modeling</a></li>
    <li><a href="#documentation">Documentation</a></li>
    <li><a href="#guide-to-api-access">Guide to api access</a></li>
    <li><a href="#contributors">Contributors</a></li>
  </ul>
</details>

# Project overview
This project seeks to automate shipments of goods to track and update them.

The project was carried out following good programming practices and contains 
the following implementations:

 * <p align="left">
   <img src="https://img.shields.io/badge/JAVA-v17-blue">
</p>

 * <p align="left">
   <img src="https://img.shields.io/badge/SPRING BOOT-v3.1.3-blue">
</p>

 * <p align="left">
   <img src="https://img.shields.io/badge/DATA PERSISTENCE-MySQL-blue">
</p>

 * <p align="left">
   <img src="https://img.shields.io/badge/DEPENDENCY MANAGER-Gradle-blue">
</p>

 * <p align="left">
   <img src="https://img.shields.io/badge/JWT AND SPRING SECURITY-grey">
</p>

 * <p align="left">
   <img src="https://img.shields.io/badge/UNIT TESTING-JUnit and Mockito-blue">
</p>

 * <p align="left">
   <img src="https://img.shields.io/badge/DOCUMENTATION-Swagger-blue">
</p>

 * <p align="left">
   <img src="https://img.shields.io/badge/CI/CD-Github Actions-blue">
</p>

# Data modeling


# Documentation
You can view the structure of the controllers in the graphical interface 
of [Swagger](https://api-rest-messenger-service-production.up.railway.app/swagger-ui/index.html), to query the CRUD endpoints.

# Guide to API access

To have full access, a user must be created, taking into account the following structure in JSON format, for example:
```xml
        {
           "id":12345,
           "name":"admin"
           "email":"admin@gmail.com",
           "password":"admin123",
           "role":"ADMIN",
           "idAdmin":5678
        }
```
To create a user of type "ADMIN" it is necessary to send an id of another administrator of the application to be able to create it, those of type "USER" it is not necessary to send the idAdmin, it can be null. For example:
```xml
        {
           "id":12345,
           "name":"user"
           "email":"user@gmail.com",
           "password":"user123",
           "role":"USER",
           "idAdmin":null
        }
```
Then, you must enter the e-mail and password, previously created to generate the access token to be able to interact with all controllers.
```xml
        {
            "email":"admin@mail.com",
            "password":"admin123"
        }
```
## Contributors 
[<img src="https://avatars.githubusercontent.com/u/122067825?v=4" width=110><br><sub>Mariany López Sánchez</sub>](https://github.com/MarianyLopez)

[<img src="https://avatars.githubusercontent.com/u/115194310?v=4" width=110><br><sub>Dilan Dahier Quintero Rivera </sub>](https://github.com/DahierQuintero)


