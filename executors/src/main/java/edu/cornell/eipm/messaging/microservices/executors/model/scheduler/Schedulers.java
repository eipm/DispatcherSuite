package edu.cornell.eipm.messaging.microservices.executors.model.scheduler;

/**
 * Configured schedulers
 *
 * @author Manuele Simi
 */
public class Schedulers {

    private FixedRate fixed;
    private FixedRate delay;
    private FixedRate cron;

    public FixedRate getDelay() {
        return delay;
    }

    public void setDelay(FixedRate delay) {
        this.delay = delay;
    }

    public FixedRate getCron() {
        return cron;
    }

    public void setCron(FixedRate cron) {
        this.cron = cron;
    }

    public FixedRate getFixed() {
        return fixed;
    }

    public void setFixed(FixedRate fixed) {
        this.fixed = fixed;
    }
}
