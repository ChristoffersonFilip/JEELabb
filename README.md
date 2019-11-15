# Schoolproject

Small project for teaching purposes.

* Wildfly
* JEE 8 
* Java 11
* Git
* Maven
* MySQL

## Wildfly configuration

Install any Wildfly release you want. I use 18.

Add a user, and place the school.cli script under the bin folder.<br>
Create database school. The script will need a mysql connector under `C:\Users`
to work. 

The script is predefined for `mysql.connector-java-8.0.12.jar`. Change location and version for your own liking.

Start Wildfly, and once running, open a new prompt, and go to the bin folder.<br>
Write `jboss-cli -c --file=school.cli`

It should say outcome success. Write `jboss-cli -c --command=:reload` to restart the server.

When the program is installed and you wish to run it later down the line write `standalone -c standalone-full.xml`

## Endpoint Testing

With the addition of new endpoint tests I have put in new error messages.
They are under the GET, POST, PUT, PATCH, DELETE with Insomnia or Postman.

Find by name
GET: localhost:8088/student/find/{name}

Find by email
GET: localhost:8088/student/find/{email}

Find all
GET: localhost:8088/student/

Delete user
DELETE: localhost:8088/student/{email}

Update user partially
PATCH: localhost:8088/student/{email}

Complete update
PUT: localhost:8088/student?forename={name}&lastname={lastname}&email={email}
