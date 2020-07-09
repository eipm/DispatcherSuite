package edu.cornell.eipm.messaging.microservices.kafka.dispatcher.scheduler.jobs.delay;

import edu.cornell.eipm.messaging.microservices.kafka.dispatcher.scheduler.JobRunner;
import edu.cornell.eipm.messaging.microservices.kafka.dispatcher.scheduler.jobs.JOBTYPE;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Job at index 5
 * @author Manuele Simi
 */
@Configuration
@Component
@ConditionalOnProperty(name = "dispatcher.schedulers.delay.job5.active")
public class DJob5 extends JobRunner {

    @Scheduled(fixedDelayString = "#{'${dispatcher.schedulers.delay.job5.when}'}", initialDelay = 5000 )
    public void delayJob() throws Exception {
        run(JOBTYPE.DELAY,5);
    }
}
