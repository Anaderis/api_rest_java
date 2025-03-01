# Creation of a REST API for my JAVAFX application

In order to access my **PostGreSQL database**, I created a REST API in Java to ease the use of the **CRUD functions** by my application. The fictional Kybu company is a seller specialized
in **Marvel**, **Nintendo** and **Blizzard** goodies.

## CRUD functions

The **administrator profile** of the application has a specific access to the CRUD functions of the API. All these functions are created in the **repositories** of the API using **JDBC**.
The **services** and **controllers** ensure the access to these functions and add specific securities to avoid empty request for instance.

Once the employee is connected, he can press **CTRL+ALT+A** in order to access the Administrator mode.
Thanks to this, the employee can **create**, **update** and **delete** an employee, site (location) or service.
The API also provides specific functions to access the employees information **by site** or **by service**.

## The CRUD buttons are accessible for all employees, sites and services

<img width="937" alt="Capture d’écran 2025-03-01 à 18 20 53" src="https://github.com/user-attachments/assets/45527009-5816-4717-b41b-8c05b148cf70" />


### These functions are displayed in pop-windows in the application

<img width="876" alt="Capture d’écran 2025-03-01 à 18 23 25" src="https://github.com/user-attachments/assets/529f5551-21ad-447f-88d4-402a950258cc" />
