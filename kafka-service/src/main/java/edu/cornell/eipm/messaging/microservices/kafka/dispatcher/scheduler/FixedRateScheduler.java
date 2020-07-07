package edu.cornell.eipm.messaging.microservices.kafka.dispatcher.scheduler;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;


/**
 * Scheduler with fixed rates for configured jobs.
 *
 * @author Manuele Simi
 */
@EnableScheduling
@Configuration
@Component
@ConditionalOnProperty(name = "dispatcher.schedulers.fixed.enable")
public class FixedRateScheduler {

}
