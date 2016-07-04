# mongodb-data-access

MongoDB Java Driver extender to support a Java Bean as an Object Model that override the default Document.class in MongoCollectionImpl

## Why?

 * An abstract collection and codec classes to support a Java Bean as an Object Model of a document inside the collections.
 * An abstract DAO to do a simple CRUD of the document inside the collections via Java Bean.

## Projects

 * **data-access-core**: Core abstract classes to simplify the implementation of using a Java Bean as a document object model.
 * **data-access-sample**: A sample project on how to use the abstract classes in core project.

## Prerequisite

 * Java 1.8 installed.
 * MongoDB 3.2 installed (note: required only for the integration testing).
 * Update data-access-sample/src/main/resources/mongodb.properties file to set the **clientURI** and **database** information.

## How to build

 * Generate the jar file.
 > mvn clean package
 
 * Generate the jar file and deployed to local repository (note: integration testing will be executed also).
 > mvn clean install
 
## Information

 * TestNG, JMockit, and Maven Surefire and Failsafe plugins was used to do an unit test (*Test.java) and integration test (*IT.java).
 * JaCoCo been used to do an unit test and integration testing coverage reports.

# Improvement

 * Use an Embedded MongoDB for integration testing.

# References

 * [MongoDB Documentation - MongoDB Drivers - Java Driver] (https://docs.mongodb.com/ecosystem/drivers/java/)
 * [MongoDB Java Driver Documentation] (http://mongodb.github.io/mongo-java-driver/)
 * [Embedded MongoDB] (https://github.com/flapdoodle-oss/de.flapdoodle.embed.mongo)
