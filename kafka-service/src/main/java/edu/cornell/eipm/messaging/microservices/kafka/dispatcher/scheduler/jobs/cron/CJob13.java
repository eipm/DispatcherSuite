package edu.cornell.eipm.messaging.microservices.kafka.dispatcher.scheduler.jobs.cron;

import edu.cornell.eipm.messaging.microservices.kafka.dispatcher.scheduler.JobRunner;
import edu.cornell.eipm.messaging.microservices.kafka.dispatcher.scheduler.jobs.JOBTYPE;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Job at index 13
 * @author Manuele Simi
 */
@Configuration
@Component
@ConditionalOnProperty(name = "dispatcher.schedulers.cron.job13.active")
public class CJob13 extends JobRunner {

    @Scheduled(cron = "#{'${dispatcher.schedulers.cron.job13.when}'}")
    public void cronJob13() throws Exception {
        run(JOBTYPE.CRON,13);
    }
}
