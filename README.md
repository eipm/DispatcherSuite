# Dispatcher Suite

![logo](doc/dispatcher-services-logo.png)

A set of microservices to interface with different messaging systems.

[![Github](https://img.shields.io/badge/github-1.4.9-green?style=flat&logo=github)](https://github.com/eipm/DispatcherSuite) [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT) [![DOI](https://zenodo.org/badge/DOI/10.5281/zenodo.14975839.svg)](https://zenodo.org/doi/10.5281/zenodo.14975839)

[![Build DispatcherSuite](https://github.com/eipm/DispatcherSuite/actions/workflows/build.yml/badge.svg)](https://github.com/eipm/DispatcherSuite/actions/workflows/build.yml)

## 🤝 License
See [LICENSE](./LICENSE)

## 📚 How to Cite
> Manuele Simi, Alexandros Sigaras, Jeff Tang, & Pantelis Zisimopoulos. (2025). eipm/DispatcherSuite: v1.4.8 (v1.4.8). Zenodo. [https://zenodo.org/records/14975856](https://zenodo.org/records/14975856)

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
