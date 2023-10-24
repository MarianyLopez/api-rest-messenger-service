<h1 align="center" > Integrative project: Messenger service Rest API :package </h1>  
<p align="right">
   <img src="https://img.shields.io/badge/STATUS-DEVELOPED-green">
</p>

This repository contains the final project of MAKAIA's Backend Development Bootcamp 
and its main purpose is to show all the topics that were taught during the last months.
This service provides a company with a structure to manage data with customer and employee
information and to create and track client shipments.

<details>
  <summary>Table of Contents</summary>
  <ul>
    <li><a href="#project-overview">Project overview</a></li>
    <li><a href="#data-modeling">Data modeling</a></li>
    <li><a href="#documentation">Documentation</a></li>
    <li><a href="#guide-to-api-access">Guide to api access</a></li>
    <li><a href="#endpoints">Endpoints</a></li>
    <li><a href="#contributors">Contributors</a></li>
  </ul>
</details>

## Project overview

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

## Data modeling

<img src="https://github.com/MarianyLopez/api-rest-messenger-service/blob/main/DataModeling.PNG">

## Documentation
You can view the structure of the controllers in the graphical interface 
of [Swagger](https://api-rest-messenger-service-production.up.railway.app/swagger-ui/index.html), to query the CRUD endpoints.

## Guide to API access

To have full access, a user must be created, taking into account the following structure in JSON format, for example:

```json
{
  "id":12345,
  "name":"admin",
  "email":"admin@gmail.com",
  "password":"admin123",
  "role":"ADMIN",
  "idAdmin":5678
}
```
To create a user of type "ADMIN" it is necessary to send an id of another administrator of the application to be able to create it, those of type "USER" it is not necessary to send the idAdmin, it can be null. For example:
```json
{
  "id":12345,
  "name":"user",
  "email":"user@gmail.com",
  "password":"user123",
  "role":"USER",
  "idAdmin":null
}
```
Then, you must enter the e-mail and password, previously created to generate the access token to be able to interact with all controllers.
```json
{
   "email":"admin@mail.com",
   "password":"admin123"
}
```

## Endpoints 

### Client management
With the following endpoints you can execute all CRUD operations (create, read, update, delte).

#### POST: api/v1/client
To create a client on the database you must enter an id (number max 10 digits), name (string), 
last name (string), phone number (string), email (string), address (string) and city (string).

**Request example:**
```json
{
  "id":12345,
  "name":"Mariany",
  "lastName": "Lopez Sanchez",
  "phoneNumber": "3037846464",
  "email": "mariany@gmail.com",
  "address":"Carrera 21c",
  "city": "Medellin"
}
```
**Expected response:**
```json
{
  "id":12345,
  "name":"Mariany",
  "lastName": "Lopez Sanchez",
  "phoneNumber": "3037846464",
  "email": "mariany@gmail.com",
  "address":"Carrera 21c",
  "city": "Medellin"
}
```
<br>

#### GET: api/v1/client/{clientId} :
To retrieve an existent client's information from the database just indicate the client's id in the path.

**Request example:**
```
http://localhost:8080/api/v1/client/12345
```
**Expected response:**
```json
{
   "id":12345,
   "name":"Mariany",
   "lastName": "Lopez Sanchez",
   "phoneNumber": "3037846464",
   "email": "mariany@gmail.com",
   "address":"Carrera 21c",
   "city": "Medellin"
}
```
<br>

#### DELETE: api/v1/client/{clientId} :
To delete an existent client from the database just indicate the client's id in the path.

**Request example:**
```
http://localhost:8080/api/v1/client/12345
```
**Expected response:**
```json
{
	"message": "Client with id 12345 was successfully removed"
}
```
<br>

#### PUT: api/v1/client/{clientId} :
To update an existent client's information indicate the client's id in the path and provide the updated information.

**Request example:**
```
http://localhost:8080/api/v1/client/12345
```
```json
{
	"id": 12345,
	"name": "Camila",
	"lastName": "López",
	"phoneNumber": 123123,
	"email": "camila@gmail.com",
	"address": "Cll 48",
	"city": "Bogotá"
}
```
**Expected response:**
```json
{
   "id": 12345,
   "name": "Camila",
   "lastName": "López",
   "phoneNumber": 123123,
   "email": "camila@gmail.com",
   "address": "Cll 48",
   "city": "Bogotá"
}
```

### Employee management:
With the following endpoints you can execute all CRUD operations (create, read, update, delte).

#### POST: api/v1/employee
To create a employee on the database you must provide an id (number max 10 digits),name (string), last name (string), phone number (number), email (string), address (string), 
city (string), seniority company (number), blood type (string) and employee type (string).

**Request example:**
```json
{
   "id":12345,
   "name":"Dilan Dahier",
   "lastName":"Quintero Rivera",
   "phoneNumber":"3017865342",
   "email":"dilandahier@gmail.com",
   "address":"Calle 57 # 24-72",
   "city":"Medellin",
   "seniorityCompany":"6",
   "bloodType":"B+",
   "employeeType":"Coordinador"
}
```
**Expected response:**
```json
{
   "id":12345,
   "name":"Dilan Dahier",
   "lastName":"Quintero Rivera",
   "phoneNumber":"3017865342",
   "email":"dilandahier@gmail.com",
   "address":"Calle 57 # 24-72",
   "city":"Medellin",
   "seniorityCompany":"6",
   "bloodType":"B+",
   "employeeType":"Coordinador"
}
```
<br>

#### GET: api/v1/employee/{employeeId} :
To retrieve an existent employee's information from the database just indicate the employee's id in the path.

**Request example:**
```
http://localhost:8080/api/v1/employee/12345
```
**Expected response:**
```json
{
   "id":12345,
   "name":"Dilan Dahier",
   "lastName":"Quintero Rivera",
   "phoneNumber":"3017865342",
   "email":"dilandahier@gmail.com",
   "address":"Calle 57 # 24-72",
   "city":"Medellin",
   "seniorityCompany":"6",
   "bloodType":"B+",
   "employeeType":"Coordinador"
}
```
<br>

#### DELETE: api/v1/employee/{employeeId} :
To delete an existent employee from the database just indicate the employee's id in the path.

**Request example:**
```
http://localhost:8080/api/v1/employee/12345
```
**Expected response:**
```json
{
   "message": "Employee with id 12345 was successfully removed"
}
```
<br>

#### PUT: api/v1/employee/{employeeId} :hammer_and_wrench:
To update an existent employee's information indicate the employee's id in the path and provide the updated information.

**Request example:**
```
http://localhost:8080/api/v1/employee/12345
```
```json
{
	"id":12345,
	"name":"Dilan",
	"lastName":"Quintero",
	"phoneNumber":"3146897563",
	"email":"dilan@gmail.com",
	"address":"Cll 26 # 8",
	"city":"Bogotá",
	"seniority":3,
	"bloodType":"B+",
	"type":"Coordinador"
}
```
**Expected response:**
```json
{
   "id":12345,
   "name":"Dilan",
   "lastName":"Quintero",
   "phoneNumber":"3146897563",
   "email":"dilan@gmail.com",
   "address":"Cll 26 # 8",
   "city":"Bogotá",
   "seniority":3,
   "bloodType":"B+",
   "type":"Coordinador"
}
```
### Shipment management:
With the following endpoints a customer can create a new shipment (a client can request several shipments) and get 
information about his shipments.

#### POST: api/v1/shipment :
To create a shipment on the database you must provide an existent client's id (number max 10 digits), origin city (string), destination city (string), the name of the person who will receive the package (string) and their phone number (number), the declared value of the package (number), the package's weight (number) and the destination address (string).

**Request example:**
```json
{
   "clientID":12345,
   "originCity":"Medellin",
   "destinationCity":"Bogotá",
   "destinationAddress":"calle 46 # 69-90",
   "namePersonReceives":"Juan Pablo",
   "phonePersonReceives":"3046303886",
   "declaredValue":19000,
   "weight": 1
}
```
**Expected response:**
The service will generate a guide number to identify the shipment and the initial status will always be Received.
```json
{
	"guideNumber": "6a483bc0-2e77-4b80-87dd-eccd81ade143",
	"deliveryStatus": "Received"
}
```
<br>

#### GET: api/v1/shipment :
To retrieve an existent shipment's information from the database just indicate guide number.

**Request example:**
```json
{
	"guideNumber": "6a483bc0-2e77-4b80-87dd-eccd81ade143"
}
```
**Expected response:**
```json
{
   "clientID":12345,
   "originCity":"Medellin",
   "destinationCity":"Bogotá",
   "destinationAddress":"calle 46 # 69-90",
   "namePersonReceives":"Juan Pablo",
   "phonePersonReceives":"3046303886",
   "declaredValue":19000,
   "weight": 1
}
```
<br>

In this service employees can update the status of a shipment and get a list of shipments by status with the following endpoints.



### GET: api/v1/shipment/list :
To obtain a list of shipments by status you must enter the status and id of an employee.

**Request example:**
```json
{
   "deliveryStatus":"Entregado",
   "employeeID": 1001419252
}
```
**Expected response:**
```json
[
   {
      "clientID":1234,
      "originCity":"Medellin",
      "destinationCity":"Bogotá",
      "destinationAddress":"calle 46 # 69-90",
      "namePersonReceives":"Juan Pablo",
      "phonePersonReceives":"3046303886",
      "declaredValue":19000,
      "weight": 1
   },
   {
      "clientID":12345,
      "originCity":"Medellin",
      "destinationCity":"Cali",
      "destinationAddress":"calle 46 # 69-90",
      "namePersonReceives":"Juan Pablo",
      "phonePersonReceives":"3046303886",
      "declaredValue":19000,
      "weight": 1
   }	
]
```
To update the status of an existing shipment enter the employee identifier, the waybill number of the delivery and the updated status.
Please note the flow:

<img src="https://github.com/MarianyLopez/api-rest-messenger-service/blob/main/DeliveryStatus.png">

**Request example:**
```json
{
   "guideNumber" : "e259def2-13e9-4a84-8c0d-3db77ff5a3e1",
   "deliveryStatus":"Entregado",
   "employeeID": 12345
}
```
**Expected response:**
```json
{
	"guideNumber": "6a483bc0-2e77-4b80-87dd-eccd81ade143",
	"deliveryStatus": "Entregado"
}
```

## Contributors 
[<img src="https://avatars.githubusercontent.com/u/122067825?v=4" width=110><br><sub>Mariany López Sánchez</sub>](https://github.com/MarianyLopez)

[<img src="https://avatars.githubusercontent.com/u/115194310?v=4" width=110><br><sub>Dilan Dahier Quintero Rivera </sub>](https://github.com/DahierQuintero)


