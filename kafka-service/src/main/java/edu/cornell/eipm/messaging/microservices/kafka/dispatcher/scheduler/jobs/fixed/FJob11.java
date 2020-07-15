package edu.cornell.eipm.messaging.microservices.kafka.dispatcher.scheduler.jobs.fixed;

import edu.cornell.eipm.messaging.microservices.kafka.dispatcher.scheduler.JobRunner;
import edu.cornell.eipm.messaging.microservices.kafka.dispatcher.scheduler.jobs.JOBTYPE;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Job at index 11
 * @author Manuele Simi
 */
@Configuration
@Component
@ConditionalOnProperty(name = "dispatcher.schedulers.fixed.job11.active")
public class FJob11 extends JobRunner {

    @Async
    @Scheduled(fixedRateString = "#{'${dispatcher.schedulers.fixed.job11.when}'}")
    public void fixedRateJob11() throws Exception {
        run(JOBTYPE.FIXED,11);
    }
}
