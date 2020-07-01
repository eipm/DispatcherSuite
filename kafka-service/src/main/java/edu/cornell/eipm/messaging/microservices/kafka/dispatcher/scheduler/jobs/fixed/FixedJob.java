package edu.cornell.eipm.messaging.microservices.kafka.dispatcher.scheduler.jobs.fixed;

import edu.cornell.eipm.messaging.microservices.executors.model.service.Action;
import edu.cornell.eipm.messaging.microservices.kafka.dispatcher.config.KafkaService;
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
abstract class FixedJob {

    protected final Logger logger = LoggerFactory.getLogger(FixedJob.class);

    @Autowired
    protected KafkaService kafkaService;

    protected void run(int jobIndex) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        String strDate = sdf.format(now);
        logger.info("Job " + jobIndex + " started at: " + strDate);
        Objects.requireNonNull(kafkaService.getSchedulers());
        for (Action action : kafkaService.getSchedulers().getFixed().getJob(jobIndex).getActions()) {
            logger.info("About to launch: " + action.getTrigger());
        }
    }
}
