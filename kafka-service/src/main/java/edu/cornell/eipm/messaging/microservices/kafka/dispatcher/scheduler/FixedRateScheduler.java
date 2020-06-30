package edu.cornell.eipm.messaging.microservices.kafka.dispatcher.scheduler;

import edu.cornell.eipm.messaging.microservices.executors.model.service.Action;
import edu.cornell.eipm.messaging.microservices.kafka.dispatcher.config.KafkaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * ScheduleEnabling with fixed rates for configured jobs.
 *
 * @author Manuele Simi
 */
@Configuration
@Component
@ConditionalOnProperty(name = "dispatcher.scheduler.enable")
public class FixedRateScheduler {

    private final Logger logger = LoggerFactory.getLogger(FixedRateScheduler.class);

    @Autowired
    private KafkaService kafkaService;

    @Scheduled(fixedRateString = "#{'${dispatcher.scheduler.fixedRate1.rate}'}")
    public void cronJob1() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        String strDate = sdf.format(now);
        logger.info("Java cron1 job started at: " + strDate);
        Objects.requireNonNull(kafkaService.getScheduler());
        for (Action action : kafkaService.getScheduler().getRate(2).getActions()) {
            logger.info("About to launch: " + action.getTrigger());
        }
    }

    @Scheduled(fixedRateString = "#{'${dispatcher.scheduler.fixedRate2.rate}'}")
    public void cronJob2() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        String strDate = sdf.format(now);
        logger.info("Java cron2 job started at: " + strDate);
        Objects.requireNonNull(kafkaService.getScheduler());
        for (Action action : kafkaService.getScheduler().getRate(2).getActions()) {
            logger.info("About to launch: " + action.getTrigger());
        }
    }

    @Scheduled(fixedRateString = "#{'${dispatcher.scheduler.fixedRate3.rate}'}")
    public void cronJob3() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        String strDate = sdf.format(now);
        logger.info("Java cron3 job started at: " + strDate);
        Objects.requireNonNull(kafkaService.getScheduler());
        for (Action action : kafkaService.getScheduler().getRate(2).getActions()) {
            logger.info("About to launch: " + action.getTrigger());
        }
    }
}
