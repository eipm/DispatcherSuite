Kafka-Dispatcher Configuration
--
A Kafka-Dispatcher instance is configured with a file in a YAML format. 

The file is composed by 4 sections:

* `kafka`
* `server`
* `dispatcher`
* `schedulers`
* `logging`

## Section: kafka
This section configures the interaction with the kafka server.

Syntax: 
```yaml
kafka:
  bootstrap-servers: host:port
  consumer:
    properties:
      sasl.mechanism: PLAIN
      sasl.jaas.config: 'org.apache.kafka.common.security.plain.PlainLoginModule required username="..." password="...";'
      security.protocol: SASL_SSL
    consumer:
      auto-offset-reset: earliest
      enable-auto-commit: true/false
      group-id: arbitrary name for this dispatcher
      missing-topics-fatal: true/false
      topics: list of comma-separated topics
```
Where:
* _bootstrap-servers_ is the list of kafka brokers to connect to:
    * for a Kafka broker, hostname and port of the kafka server instance(s)
    * for an Event Hubs namespace, [FQDN namespace](EVENT_HUBS.md):9093
* _sasl.mechanism_: 
    * for a Kafka broker, use `GSSAPI`
    * for an Event Hubs namespace, use `PLAIN`
* _sasl.jaas.config_  is the  SASL configuration for the brokers:
    * for a Kafka broker, if authentication is required, specify username and password, otherwise leave empty
    * for an Event Hubs namespace, use _$ConnectionString_ as username and the [connection string ](EVENT_HUBS.md) as password
* _auto-offset-reset_: 'earliest' ensures the new consumer group will get the message sent in case the container started after the send was completed
* _security.protocol_:
    * for a Kafka broker, use `SASL_SSL`
    * for an Event Hubs namespace, use `PLAINTEXT`
* _enable-auto-commit_: automatically acknowledge the kafka server that a message has been received 
* _group-id_ is the identifier used by the dispatcher to register to the kafka server.
* _missing-topics-fatal_: if true, the service exits if any of the consumer topics does not exist
* _topics_ is the list of topics the dispatcher will register for notifications
 
### About the Group Id
Kafka consumers label themselves with a consumer group name, and each record published to a topic is delivered to one consumer instance within each subscribing consumer group. Consumer instances can be in separate processes or on separate machines.

If all the consumer instances have the same consumer group, then the records will effectively be load balanced over the consumer instances.

If all the consumer instances have different consumer groups, then each record will be broadcast to all the consumer processes.

## Section: server
This section defines the endpoint for the dispatcher.

```yml
server:
  port: 8080
  servlet:
    context-path: /dispatcher
```
Where:

* _port_ is the port of the embedded Tomcat server where the application can be reached
* _context-path_ is the context path of the webapp

The dispatcher is reached at _http://hostname:8080/dispatcher_

## Section: server with SSL
```yml
server:
  port: 8443
  ssl:
    key-store: /keystore.p12
    key-store-password: <password of the keystore>
    keyStoreType: PKCS12
    keyAlias: tomcat
  servlet:
    context-path: /dispatcher
```
Reference: 
* To generate a self-signed certificate for the keystore, see https://docs.oracle.com/javase/7/docs/technotes/tools/solaris/keytool.html

## Section: dispatcher
This section configures the behavior of the dispatcher as consumer of messages from Kafka. When acting as producer of messages the dispatcher does not need any specific configuration.

What we configure here are basically the actions we want the dispatcher performs upon receiving a message from the topics of interest (see kakfka section).

For each topic (defined at the first level) we configure a list of triggers (something that the dispatcher will launch) and an optional reply message to send back to kafka if the trigger has successfully started. The format of the reply message is the same of query parameters in a URL (param=value&param=value...).


```yaml
dispatcher:
  topics:
    - name: Topic1
      actions:
        - trigger: command ${param1} ${param2} ... 
          reply:
            topic: TopicA
            payload: anotherParam1=${param1}&anotherParam2=${param2}
        - trigger: command2 ${param2} ... 
           reply:
            topic: TopicC
            payload: anotherParam1=${param1}
    - name: Topic2
      actions:
         - trigger: http://url?param=${param1}&param2=${param2}                     
    - name: Topic3
      ...
```

**IMPORTANT**: The topic's names in the dispatcher section must be included in the topic list configured in the kafka section. This section only defines the actions, not what topics the dispatcher will listen from.

### Actions
When the instance of Dispatcher service is configured to be notified about messages published in a selected topic,
one or more triggers can be started upon the reception of each message.

A trigger is "something" executed by the Dispatcher.

Version 1.x of the dispatcher supports two types of trigger:
* _Local Command_ A command executed on the machine where the dispatcher is running
* _Remote Call_ A URL to invoke (e.g. http//:www.google.com?query${terms})

#### Trigger configuration

A trigger is a string representing a command to execute locally or a URL.

A trigger may have placeholders in the format `$name`. If `name` is a valid key in the [payload](kafka-service/doc/PAYLOAD.md) of the message, then it is replaced with its associated value.

`local` forces the trigger to be executed inside the Docker container when running as dockerized application.

## Section: schedulers
Kafka-Dispatcher provides support for task scheduling in the schedulers section.
````yaml
schedulers:
    enable: true
    fixed:
      job1:
        active: true/false
        when: time in ms
        actions:
          - trigger: command1
          - trigger: command2
      job2:
        active: true/false
        when: time in ms
        actions:
          - trigger: command3
    
    cron:
      job1:
        active: true/false
        when: "cron expression"
        actions:
          - trigger: command4

````
For further details about how to configure this section, see [SCHEDULERS](SCHEDULERS.md)

## Section: logging
The level of the output logs can be set in the logging section as follows:

````yaml
logging:
  level:
    org.apache.kafka: level
    edu.cornell.eipm.messaging.microservices: level
````
The 5 available levels are:

* ERROR
* WARNING
* INFO
* DEBUG
* TRACE

## A Complete Configuration Example
The following example configures a dispatcher instance as follows:

* it registers the instance to be notified for messages published in 2 topics of interests 
* for the each topic, one action is defined:
  * when a message from oncorseq.sequencing.in_progress is received, a nextflow process is triggered. If the [payload](../../PAYLOAD.md) includes a _sampleID_ key, its value is replaced in the trigger before executing it. 
  * when a message from oncorseq.sequencing.pipeline_initialized is received, a command echoing the value of the _pipeline_ parameter is executed.

```yaml
kafka:
  bootstrap-servers: hostname.med.cornell.edu:port
  properties:
    sasl.mechanism: GSSAPI
    sasl.jaas.config: 'org.apache.kafka.common.security.plain.PlainLoginModule required username="" password="";'
    security.protocol: PLAINTEXT
  consumer:
    auto-offset-reset: earliest
    group-id: kafka-dispatcher
    enable-auto-commit: true
    missing-topics-fatal: false
    topics: oncorseq_sequencing_pipeline_initialized,oncorseq_sequencing_in_progress,oncorseq_sequencing_analysis_started

server:
  port: 8080
  servlet:
    context-path: /dispatcher

dispatcher:
  topics:
    - name: oncorseq.sequencing.in_progress
      actions:
        - trigger: nextflow /path/main.nf -w /workingDir -c /path/nextflow-manuele.config --sampleID ${sampleID} --dispatcherURL http://localhost:8080/dispatcher/ --resourceDir /path    
          force-local: true
          reply:
            topic: oncorseq.sequencing.pipeline_initialized
            payload: sampleID=${sampleID}&status=initialized
    - name: oncorseq.sequencing.pipeline_initialized
      actions:
        - trigger: echo "Good job with ${pipeline}"

logging:
  level:
    org.apache.kafka: INFO
    edu.cornell.eipm.messaging.microservices: TRACE
```


See further configuration examples [here](../configs).