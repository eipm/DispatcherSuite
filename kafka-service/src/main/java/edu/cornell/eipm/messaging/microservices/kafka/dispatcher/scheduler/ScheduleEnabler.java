package edu.cornell.eipm.messaging.microservices.kafka.dispatcher.scheduler;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@Configuration
@ConditionalOnProperty(name = "dispatcher.schedulers.enable")
public class ScheduleEnabler {
}
