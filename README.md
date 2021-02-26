# Pixelizator

## How to run

```shell
./mvnw spring-boot:run
```

## Setup

* Install MySQL - `brew install mysql`
* Run MySQL - `brew services start mysql`
* Add MySQL to start on login - `brew services start mysql`
* Run MySQL - `mysql -uroot`

## MySQL

* Show existed databases - `SHOW DATABASES;`
* Create a database - `CREATE DATABASE db_name;`
* Delete a database - `DROP DATABASE [IF EXISTS] db_name;`
* Connect to a database - `USE db_name;`
* Show current database - `SELECT DATABASE();`
* Create a table - see [init.sql](./src/main/resources/data/init.sql)
* List all tables in a database - `SHOW TABLES;`

## Engage

Never understood that need to be done in this section.

## Investigate

#### What is an app server?

Well, it's pretty straightforward - it's a server that hosts applications.
Tomcat is not an app server, it's a web container, or in other terms

####   

?? To Spring or not to Spring? What is the question.

* Looks like an
  appropriate [example](https://spring.io/guides/gs/uploading-files/
  ).

## How to run an SQL script on MySQL

https://www.tutorialspoint.com/how-to-run-sql-script-using-jdbc

```xml

<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis</artifactId>
    <version>3.4.5</version>
</dependency>
```

```
ScriptRunner sr = new ScriptRunner(con);
// Creating a reader object
Reader reader = new BufferedReader(new FileReader("E:\\sampleScript.sql"));
//Running the script
sr.runScript(reader);
```

## Pixilization

* [Wiki](https://en.wikipedia.org/wiki/Pixelization) article.
* [Stackoverflow](
  https://stackoverflow.com/questions/15777821/how-can-i-pixelate-a-jpg-with-java
  ) algorithm example.