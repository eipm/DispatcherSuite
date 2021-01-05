![logo](doc/dispatcher-services-logo.png)

A set of microservices to interface with different messaging systems.

## Modules

* [Kafka-Dispatcher Service](kafka-service/README.md) - to interface with  Apache Kafka brokers (on-prem or on the cloud, like Microsoft EventHubs or AWS MSK)
* [Azure EventHubs-Dispatcher Service](eventhubs-service/README.md) (**EXPERIMENTAL SUPPORT**)- to natively interface with a Microsoft EventHubs service on the cloud (Azure)
* [Executors Backend](executors) - common backend for all services


## Built With
* [Spring Boot](https://spring.io/projects/spring-boot) - A framework that makes it easy to create stand-alone, production-grade Spring-based Applications
* [OpenJDK](https://openjdk.java.net/) - A free and open-source implementation of the Java Platform, Standard Edition
* [Maven](https://maven.apache.org/) - For dependency management