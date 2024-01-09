![logo](doc/dispatcher-services-logo.png)

A set of microservices to interface with different messaging systems.

## Modules
* [Kafka-Dispatcher Service](kafka-service/README.md) - to interface with Apache Kafka brokers (on-prem or on the cloud, like Microsoft EventHubs or AWS MSK)
* [Executors Backend](executors) - common backend for all services


## Built With
* [Spring Boot](https://spring.io/projects/spring-boot) - A framework that makes it easy to create stand-alone, production-grade Spring-based Applications
* [OpenJDK](https://openjdk.java.net/) - A free and open-source implementation of the Java Platform, Standard Edition
* [Maven](https://maven.apache.org/) - For dependency management
* [Maven Spotless Plugin](https://github.com/diffplug/spotless/tree/main/plugin-maven) - For code formatting

## Formatting rules
The source code of this project is formatted to comply with
[Google Java Style](https://google.github.io/styleguide/javaguide.html).
