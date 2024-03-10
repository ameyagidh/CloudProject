# Webservice

## Prerequisites
This project requires **Java 8** and **Maven** to be set up.  
To set up Maven, follow the tutorial in this [link](https://www.baeldung.com/install-maven-on-windows-linux-mac).

## Database
We utilize **MySQL** and **JPA** for performing database operations.

## Environment Variables or VM Arguments
Set the following environment variables or VM arguments:
- salt: `<your chosen salt>`
- dbpwd: `<your database password>`

Example VM arguments:
```bash
salt=mysalt;dbpwd=mypassword
```

## Steps to Run the Application
Navigate to the project folder using the command line or terminal.

- To build:
```bash
mvn --batch-mode --update-snapshots install
```

- To run tests:
```bash
mvn --batch-mode --update-snapshots test
```

- To start the application:
```bash
cd target
java -jar assignment-1.0-SNAPSHOT.jar
```

You can check the health of the application at [http://localhost:8081/healthz](http://localhost:8081/healthz).