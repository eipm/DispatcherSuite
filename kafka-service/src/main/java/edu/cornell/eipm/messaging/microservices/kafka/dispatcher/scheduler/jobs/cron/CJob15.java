package edu.cornell.eipm.messaging.microservices.kafka.dispatcher.scheduler.jobs.cron;

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
@ConditionalOnProperty(name = "dispatcher.schedulers.cron.job15.active")
public class CJob15 extends JobRunner {

    @Scheduled(cron = "#{'${dispatcher.schedulers.cron.job15.when}'}")
    public void cronJob15() throws Exception {
        run(JOBTYPE.CRON,15);
    }
}
