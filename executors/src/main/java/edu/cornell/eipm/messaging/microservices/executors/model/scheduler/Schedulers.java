package edu.cornell.eipm.messaging.microservices.executors.model.scheduler;

/**
 * Configured schedulers
 *
 * @author Manuele Simi
 */
public class Schedulers {

    private Fixed fixed;
    private Delay delay;
    private Cron cron;

    public Delay getDelay() {
        return delay;
    }

    public void setDelay(Delay delay) {
        this.delay = delay;
    }

    public Cron getCron() {
        return cron;
    }

    public void setCron(Cron cron) {
        this.cron = cron;
    }

    public Fixed getFixed() {
        return fixed;
    }

    public void setFixed(Fixed fixed) {
        this.fixed = fixed;
    }
}
