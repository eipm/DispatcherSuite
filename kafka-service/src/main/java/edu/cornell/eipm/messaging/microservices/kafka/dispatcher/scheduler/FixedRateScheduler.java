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
 * Scheduler with fixed rates for configured jobs.
 *
 * @author Manuele Simi
 */
@Configuration
@Component
@ConditionalOnProperty(name = "dispatcher.schedulers.fixed.enable")
public class FixedRateScheduler {

}
