package edu.cornell.eipm.messaging.microservices.kafka.dispatcher.scheduler.jobs.fixed;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Job at index 3
 * @author Manuele Simi
 */
@Configuration
@Component
@ConditionalOnProperty(name = "dispatcher.schedulers.fixed.job3.active")
public class Job3 extends FixedJob {

    @Scheduled(fixedRateString = "#{'${dispatcher.schedulers.fixed.job3.rate}'}")
    public void fixedRateJob1() throws Exception {
        run(3);
    }
}
