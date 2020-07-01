package edu.cornell.eipm.messaging.microservices.kafka.dispatcher.config;

import edu.cornell.eipm.messaging.microservices.executors.model.scheduler.Schedulers;
import edu.cornell.eipm.messaging.microservices.executors.model.service.Service;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * @author Manuele Simi
 */
@Component
@Validated
@Configuration
@ConfigurationProperties("dispatcher")
public class KafkaService {

    public Service service;

    public Schedulers schedulers;

    public Service getService() {
        return service;
    }

    public Schedulers getSchedulers() {
        return schedulers;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public void setSchedulers(Schedulers schedulers) {
        this.schedulers = schedulers;
    }
}
