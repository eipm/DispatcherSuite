package edu.cornell.eipm.messaging.microservices.dispatcher.schedulers;

import edu.cornell.eipm.messaging.microservices.dispatcher.config.SchedulerConfigurations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * CronScheduler for configured schedulers.
 *
 * @author Manuele Simi
 */
@Configuration
@Component
public class CronScheduler {

    private final Logger logger = LoggerFactory.getLogger(FixedRateScheduler.class);

    @Autowired
    private SchedulerConfigurations schedulerConfigurations;

    //@Scheduled(cron = "#{'${scheduler.cron1.cron}'}")
    public void cronJob1() throws Exception {


    }

    //@Scheduled(cron = "#{'${scheduler.cron2.cron}'}")
    public void cronJob2() throws Exception {


    }

    //@Scheduled(cron = "#{'${scheduler.cron3.cron}'}")
    public void cronJob3() throws Exception {


    }
}