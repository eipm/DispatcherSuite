package edu.cornell.eipm.messaging.microservices.kafka.dispatcher.scheduler;

import edu.cornell.eipm.messaging.microservices.executors.model.scheduler.Job;
import edu.cornell.eipm.messaging.microservices.executors.model.service.Action;
import edu.cornell.eipm.messaging.microservices.executors.runtime.ExecutorService;
import edu.cornell.eipm.messaging.microservices.executors.runtime.MODE;
import edu.cornell.eipm.messaging.microservices.kafka.dispatcher.config.KafkaService;
import edu.cornell.eipm.messaging.microservices.kafka.dispatcher.scheduler.jobs.JOBTYPE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;

/**
 * Base job runner.
 *
 * @author Manuele Simi
 */
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
        logger.trace("Scheduler for {} Job {} started at {} ", type, jobIndex, strDate);
        logger.trace("{} Job {} configured when at {} ", type, jobIndex, job.getWhen());

        job.getActions().forEach(action -> {
            logger.debug("About to launch: " + action.getTrigger());
            try {
                ExecutorService.select(action).
                        execute(Collections.emptyMap(), action.isLocal(), MODE.NON_BLOCKING);
                logger.debug("{} Job at index {} successfully executed", type, jobIndex);
            } catch (IOException e) {
                logger.error("Failed to run {} Job at index {}", type, jobIndex);
            }
        });
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
