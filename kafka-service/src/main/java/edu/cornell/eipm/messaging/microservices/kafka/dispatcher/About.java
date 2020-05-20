package edu.cornell.eipm.messaging.microservices.kafka.dispatcher;

public class About {

    private String component = "Kafka-Dispatcher Service";
    private String version = "1.1.4";
    private final String content;


    public About() {
        this.content = String.format("%s %s", component, version);
    }

    public String getComponent() {
        return component;
    }

    public String getVersion() {
        return version;
    }

    public String getContent() {
        return content;
    }
}
