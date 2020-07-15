package edu.cornell.eipm.messaging.microservices.kafka.dispatcher.scheduler.jobs.delay;

import edu.cornell.eipm.messaging.microservices.kafka.dispatcher.scheduler.JobRunner;
import edu.cornell.eipm.messaging.microservices.kafka.dispatcher.scheduler.jobs.JOBTYPE;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Job at index 13
 * @author Manuele Simi
 */
@Configuration
@Component
@ConditionalOnProperty(name = "dispatcher.schedulers.delay.job13.active")
public class DJob13 extends JobRunner {

    @Async
    @Scheduled(fixedDelayString = "#{'${dispatcher.schedulers.delay.job13.when}'}")
    public void delayJob15() throws Exception {
        run(JOBTYPE.DELAY,13);
    }
}
