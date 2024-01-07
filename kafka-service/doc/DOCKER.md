Running Kakfa-Dispatcher with Docker
---
Kafka-Dispatcher can run inside its Docker image (_eipm/kafka-dispatcher_).

How to start container depends on the triggers to launch (either from [messages](CONFIGURATION.md) or from [scheduled jobs](SCHEDULERS.md)).

### Triggers executed in the Hosting Node (SSH wrapper)
If we want the triggers to be executed inside the hosting node environment (the node running the docker container), 
the following requirement must be satisfied:

**Requirement**: the container must be able to ssh the hosting machine to launch local commands.

A docker container with an instance of the dispatcher is started with the following command:

````bash
    docker run --rm \
        -e HOST_HOSTNAME=$(hostname) \
        -e HOST_USER=$LOGNAME \
        -e HOST_USER_ID=$(id -u) \
        -e DISPATCHER_PORT=<KD_CONFIG_PORT> \
         --net=host \
        --userns=host \
        -v <HOME>/.ssh/:/ssh/:ro \
        -v <ABSOLUTE_PATH>/log/:/log \        
        -v <ABSOLUTE_PATH>/application.yml:/config/application.yml cgen/kafka-dispatcher:latest
````
where 

* the file mounted as _/config/application.yml_ is the YAML configuration file (see [CONFIGURATION](CONFIGURATION.md))
* the folder mounted under _/ssh_ must include a public key (_id_rsa.pub_) 
authorized to access the hosting machine (this is needed to execute local actions)
* KD_CONFIG_PORT is the port configured inside <ABSOLUTE_PATH>/application.yml (see [CONFIGURATION](CONFIGURATION.md))

### Triggers executed inside the Docker Container
If the docker container has all it needs to execute the triggers (e.g. mounted volumes, scripts, commands, etc.), 
you can start a container with an instance of the dispatcher as follows:

````bash
    docker run --rm \
        --userns=host \
        --net=host \
        -e DISPATCHER_PORT=<KD_CONFIG_PORT> \
        -v <ABSOLUTE_PATH>/log/:/log \                
        -v <ABSOLUTE_PATH>/application.yml:/config/application.yml cgen/kafka-dispatcher:latest
````
 
where 
* KD_CONFIG_PORT is the port configured inside <ABSOLUTE_PATH>/application.yml (see [CONFIGURATION](CONFIGURATION.md))
* LOCAL_PORT is the port on the node to bind (must be available)

In this example it binds to the same port number in the hosting node, but if 8080 is not available, you can bind to a new port with _NEW PORT:8080_
* the file mounted as _/config/application.yml_ is the YAML configuration file (see [CONFIGURATION](CONFIGURATION.md))
* the folder mounted under _/ssh_ must include a public key (_id_rsa.pub_) authorized to access the hosting machine (this is needed to execute local actions)
* KD_CONFIG_PORT is the port configured inside <ABSOLUTE_PATH>/application.yml (see [CONFIGURATION](CONFIGURATION.md))

### Expected Output Logs
If the configuration passed to the instance is correct, you should see the following output:

~~~

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.7.10.RELEASE)

08:42:20.657 [main] INFO  e.c.e.m.m.k.dispatcher.DispatcherApp - Starting DispatcherApp v1.1.3 on MAC162555 with PID 48432 (/Users/manuelesimi/EIPM/DispatcherSuite/kafka-service/target/kafka-service-1.1.3.war started by manuelesimi in /Users/manuelesimi/EIPM/DispatcherSuite/kafka-service)
08:42:20.662 [main] DEBUG e.c.e.m.m.k.dispatcher.DispatcherApp - Running with Spring Boot v2.2.2.RELEASE, Spring v5.2.2.RELEASE
08:42:20.662 [main] INFO  e.c.e.m.m.k.dispatcher.DispatcherApp - No active profile set, falling back to default profiles: default
08:42:20.770 [main] INFO  o.s.b.d.e.DevToolsPropertyDefaultsPostProcessor - For additional web related logging consider setting the 'logging.level.web' property to 'DEBUG'
08:42:22.444 [main] INFO  o.s.b.w.e.tomcat.TomcatWebServer - Tomcat initialized with port(s): 8080 (http)
08:42:23.391 [main] INFO  o.s.web.context.ContextLoader - Root WebApplicationContext: initialization completed in 2621 ms
08:42:24.154 [main] INFO  o.s.s.c.ThreadPoolTaskExecutor - Initializing ExecutorService 'applicationTaskExecutor'
08:42:24.436 [main] INFO  o.s.s.c.ThreadPoolTaskScheduler - Initializing ExecutorService 'taskScheduler'
08:42:25.174 [main] INFO  o.s.s.c.ThreadPoolTaskScheduler - Initializing ExecutorService
08:42:25.271 [main] INFO  o.s.b.w.e.tomcat.TomcatWebServer - Tomcat started on port(s): 8080 (http) with context path '/dispatcher'
08:42:25.276 [main] INFO  e.c.e.m.m.k.dispatcher.DispatcherApp - Started DispatcherApp in 5.464 seconds (JVM running for 6.286)
edu.cornell.eipm.messaging.microservices.kafka.dispatcher.config.KafkaService$$EnhancerBySpringCGLIB$$fca011f7@1a451d4d
08:42:26.065 [$Default-0-C-1] INFO  o.s.k.l.KafkaMessageListenerContainer - $Default: partitions revoked: []
08:42:32.118 [$Default-0-C-1] INFO  o.s.k.l.KafkaMessageListenerContainer - $Default: partitions assigned: [ ... listening topics here ...]
~~~

### Running with SSL
Kafka Dispatcher uses the Java Keystore (JKS) technology to handle certificates. A Java KeyStore is a repository of security certificates – either authorization certificates or public key certificates – plus corresponding private keys, used for instance in SSL encryption.
                                                                           
Before starting the Docker container, you need to create a keystore repository with the certificates.

See https://www.digitalocean.com/community/tutorials/java-keytool-essentials-working-with-java-keystores for a good tutorial on how to  work with JKS.

You can start a container with an instance of the dispatcher on HTTPS as follows:

    docker run -p 8443:8443 --rm \
        -e HOST_HOSTNAME=$(hostname) \
        -e HOST_USER=$LOGNAME \
        -e HOST_USER_ID=$(id -u)
        --userns=host
        -v <HOME>/.ssh/:/ssh/:ro \
        -v <ABSOLUTE_PATH>/log/:/log \
        -v <my keystore location>:/keystore.p12 \
        -v <ABSOLUTE_PATH>/application.yml:/config/application.yml eipm/kafka-dispatcher:latest

## Other Options
For other supported options, see the [docker run documentation](https://docs.docker.com/engine/reference/commandline/run/).