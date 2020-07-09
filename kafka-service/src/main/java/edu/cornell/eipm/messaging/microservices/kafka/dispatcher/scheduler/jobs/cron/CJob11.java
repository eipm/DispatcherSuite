package edu.cornell.eipm.messaging.microservices.kafka.dispatcher.scheduler.jobs.cron;

import edu.cornell.eipm.messaging.microservices.kafka.dispatcher.scheduler.JobRunner;
import edu.cornell.eipm.messaging.microservices.kafka.dispatcher.scheduler.jobs.JOBTYPE;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Job at index 11
 * @author Manuele Simi
 */
@Configuration
@Component
@ConditionalOnProperty(name = "dispatcher.schedulers.cron.job11.active")
public class CJob11 extends JobRunner {

    @Scheduled(cron = "#{'${dispatcher.schedulers.cron.job11.when}'}")
    public void cronJob() throws Exception {
        run(JOBTYPE.CRON,11);
    }
}
