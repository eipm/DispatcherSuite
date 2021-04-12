package edu.cornell.eipm.messaging.microservices.kafka.dispatcher;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class About {

    private Properties properties;
    private String component;
    private String version;


    public About() {
        this.read("kd-info.txt");
        this.component = this.getComponent();
        this.version = this.getVersion();
    }

    public String getComponent() {
        return getProperty("kd.name");
    }

    public String getVersion() {
        return getProperty("kd.version");
    }

    private void read(String propertyFileName) {
        InputStream is = getClass().getClassLoader()
                .getResourceAsStream(propertyFileName);
        this.properties = new Properties();
        try {
            this.properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String propertyName) {
        return this.properties.getProperty(propertyName);
    }
}
