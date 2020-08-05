Kafka-Dispatcher on Amazon Managed Streaming for Apache Kafka (Amazon MSK)
---

Amazon Managed Streaming for Apache Kafka (Amazon MSK) is a fully managed service that enables you to build and run applications that use Apache Kafka to process streaming data. 
You can use Amazon MSK to create clusters that use Apache Kafka protocol version 1.1.1, 2.2.1, 2.3.1, or 2.4.1. 
The latest version of the Kafka-Dispatcher uses Apache Kafka 2.3.1.

## Overview

A general introduction to AWS MSK:

https://aws.amazon.com/msk/
https://aws.amazon.com/msk/what-is-kafka/

## How to create an Amazon MSK Cluster
https://docs.aws.amazon.com/msk/latest/developerguide/create-cluster.html

From the Kafka-Dispatcher point of view, the most relevant settings for the creation process are:
#### 1. Authentication
If you want to authenticate the identity of clients, choose _Enable TLS client authentication_ by selecting the box next to it. 
Kafka-Dispatcher supports both PLAINTEXT and TSL authentication.

#### 2. Bootstrap servers
The creation of a cluster activates a list of host/port pairs for establishing the initial connection to the Kafka cluster.
Let's suppose we create a cluster named _clingen-dev1_. We will get a list of host/port pairs similar to this:   
````  
TLS
b-2.clingen-dev1.o3agq0.c2.kafka.us-east-1.amazonaws.com:9094,b-1.clingen-dev1.o3agq0.c2.kafka.us-east-1.amazonaws.com:9094

Plaintext
b-2.clingen-dev1.o3agq0.c2.kafka.us-east-1.amazonaws.com:9092,b-1.clingen-dev1.o3agq0.c2.kafka.us-east-1.amazonaws.com:9092
````  
These servers must be reported in the KD configuration.

#### 3. Encryption
Amazon MSK provides data encryption options that you can use to meet strict data management requirements. 
The certificates that Amazon MSK uses for encryption must be renewed every 13 months.

https://docs.aws.amazon.com/msk/latest/developerguide/msk-encryption.html

## How to create Topics
Topics are not automatically created within an MSK instance. Therefore, any topic you wish the KD instance 
publishes or listens from must be created:

https://docs.aws.amazon.com/msk/latest/developerguide/create-topic.html

## Useful AWS Commands to inspect a Cluster:

Looking at the configuration, you might have to update the brokers and zookeeper connect strings to the values for the Amazon MSK cluster.
Using the aws cli:  
* aws kafka list-clusters (to get the list of clusters, copy the ClusterArn for the cluster you want to connect to)
* aws kafka describe-cluster --cluster-arn <copied cluster arn>|grep Zookeeper (to get the zookeeper connect string)
* aws kafka get-bootstrap-brokers --cluster-arn <copied cluster arn> (to get the bootstrap brokers)

## Kafka-Dispacther Configuration
Connection to AWS MSK is like connecting to any Kafka cluster and  it depends on how you had setup your MSK cluster.
### Plaintext 
If the authentication is _PLAINTEXT_, its as simple as setting bootstrap.servers. 
See [sample-msk-plaintext.yml](../configs/sample-msk-plaintext.yml).

### TSL
If the authentication is _TSL_, you need to specify the property **security.protocol** and set it to **SSL**. 
See [sample-msk-TSL.yml](../configs/sample-msk-TSL.yml).

In both the above configurations, you need to set the appropriate **bootstrap-servers** according to the authentication
selected at cluster creation time.

### Encryption
A third type of configuration is possible by using encryption of messages. In this case, you first need to 
[create a truststore file](https://docs.oracle.com/cd/E19509-01/820-3503/6nf1il6er/index.html) and then set the following
properties to access the MSK cluster:
  
**security.protocol: SSL**
  
**ssl.truststore.location: path-to_trustore.jks**
  
**ssl.truststore.password: pwd**

Reference: 
https://docs.aws.amazon.com/msk/latest/developerguide/msk-working-with-encryption.html

## Ports to Open
There are some outbound ports to open to use kafka protocol to communicate with Amazon MSK:
* 9092 is used for plaintext authentication
* 9094 is used for TSL-based authentication

## Other useful links
* https://docs.aws.amazon.com/msk/latest/developerguide/getting-started.html
* https://docs.aws.amazon.com/msk/latest/developerguide/MSKDevGuide.pdf
* https://docs.aws.amazon.com/msk/latest/developerguide/produce-consume.html
* https://www.onooks.com/connection-to-msk-using-spring-kafka/
* https://stackoverflow.com/questions/59935284/connection-to-msk-using-spring-kafka
* https://community.boomi.com/s/article/Kafka-connector-Integrating-with-Amazon-Managed-Streaming-for-Apache-Kafka-MSK#MSKAuth
* https://github.com/spring-cloud/spring-cloud-stream-binder-kafka/issues/668
* https://docs.confluent.io/2.0.0/kafka/ssl.html#configuring-kafka-clientsonly
