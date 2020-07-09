package edu.cornell.eipm.messaging.microservices.kafka.dispatcher.scheduler;

import edu.cornell.eipm.messaging.microservices.executors.model.scheduler.Job;
import edu.cornell.eipm.messaging.microservices.executors.model.service.Action;
import edu.cornell.eipm.messaging.microservices.kafka.dispatcher.config.KafkaService;
import edu.cornell.eipm.messaging.microservices.kafka.dispatcher.scheduler.jobs.JOBTYPE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Configuration
@Component
public abstract class JobRunner {


    protected final Logger logger = LoggerFactory.getLogger(JobRunner.class);

    @Autowired
    protected KafkaService kafkaService;

    protected void run(JOBTYPE type, int jobIndex) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        String strDate = sdf.format(now);
        Objects.requireNonNull(kafkaService.getSchedulers());
        Job job;
        switch (type) {
            case FIXED:
                job = this.getFixedJob(jobIndex);
                break;
            case CRON:
                job = this.getCronJob(jobIndex);
                break;
            case DELAY:
                job = this.getDelayJob(jobIndex);
                break;
             default:
                 throw new IllegalArgumentException("Invalid job type");
        }
        logger.info("Scheduler fo Job " + jobIndex + " started at: " + strDate);
        for (Action action : job.getActions()) {
            logger.info("About to launch: " + action.getTrigger());
        }
    }

    private Job getCronJob(int jobIndex) {
        return kafkaService.getSchedulers().getCron().getJob(jobIndex);
    }

    private Job getDelayJob(int jobIndex) {
        return kafkaService.getSchedulers().getDelay().getJob(jobIndex);
    }

    private Job getFixedJob(int jobIndex) {
        return kafkaService.getSchedulers().getFixed().getJob(jobIndex);
    }
}
