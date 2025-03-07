![logo](../doc/dispatcher-services-logo.png)

Kafka Dispatcher Service
----
A microservice project based on Spring Boot to send/receive/manage messages to/from a Kafka Broker.

[Apache Kafka](https://kafka.apache.org/) is increasingly used as the message broker in event-driven 
architectures with asynchronous microservices. Kafka-Dispatcher can interface with any broker supporting
[Kafka Protocol](https://kafka.apache.org/protocol) up to version 2.6.7.


The service delivers the following main features:
* publish messages to any topic
* register for notifications for messages on selected topics
* for each message received, trigger one or more actions
* generic and configurable with respect to topics and actions
* support for task scheduling

The service works with one of the followings:
* an instance of [Apache Kafka](https://kafka.apache.org/) running on some on-prem resource or on the cloud
* a managed instance of Microsoft [Event Hubs](https://azure.microsoft.com/en-us/services/event-hubs/) on the cloud with 
the Event Hubs for Kafka feature enabled
* a managed instance of Amazon [MSK](https://aws.amazon.com/msk/) on the cloud

## Quick Start
1. Prepare the [configuration](doc/CONFIGURATION.md) file.

2. Start the Kafka-Dispatcher as [local application](doc/APPLICATION.md) or with [Docker](doc/DOCKER.md).

## Documentation

* [Configuration](doc/CONFIGURATION.md)
* [Message Payload and Actions](doc/PAYLOAD.md)
* [Running as App](doc/APPLICATION.md)
* [Running with Docker](doc/DOCKER.md)
* [How to publish Messages](doc/INTERFACE.md)
* [Scheduling Jobs](doc/SCHEDULERS.md)
* [Enabling HTTPS](doc/HTTPS.md)


## Messaging systems
* [Setting up an Apacke Kafka instance](doc/APACHE_KAFKA.md)
* [Setting up Kafka-enabled Event Hubs on Azure](doc/EVENT_HUBS.md)
* [Setting up an MSK cluster on Amazon AWS](doc/AMAZON_MSK.md)

## For Developers:
See [How to build the project](doc/FOR_DEVELOPERS.md).

## Built With
* [Spring Boot](https://spring.io/projects/spring-boot) - A framework that makes it easy to create stand-alone, production-grade Spring-based Applications
* [Spring Kafka](https://spring.io/projects/spring-kafka) - Spring concepts for the development of Kafka-based messaging solutions
* [OpenJDK](https://openjdk.java.net/) - A free and open-source implementation of the Java Platform, Standard Edition
* [Maven](https://maven.apache.org/) - For dependency management
