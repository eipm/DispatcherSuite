package edu.cornell.eipm.messaging.microservices.kafka.dispatcher.scheduler.jobs.fixed;

import edu.cornell.eipm.messaging.microservices.kafka.dispatcher.scheduler.JobRunner;
import edu.cornell.eipm.messaging.microservices.kafka.dispatcher.scheduler.jobs.JOBTYPE;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Job at index 15
 * @author Manuele Simi
 */
@Configuration
@Component
@ConditionalOnProperty(name = "dispatcher.schedulers.fixed.job15.active")
public class Job15 extends JobRunner {

    @Scheduled(fixedRateString = "#{'${dispatcher.schedulers.fixed.job15.when}'}", initialDelay = 5000)
    public void fixedRateJob() throws Exception {
        run(JOBTYPE.FIXED,15);
    }
}
