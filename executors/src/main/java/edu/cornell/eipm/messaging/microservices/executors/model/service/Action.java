package edu.cornell.eipm.messaging.microservices.executors.model.service;

/**
 * An action associated to the {@link Topic}
 *
 * @author Manuele Simi
 */
public class Action {

    private String trigger;
    private Reply reply;
    /**
     * if the action must be executed locally (no ssh wrapper)
     */
    private boolean local;

    public String getTrigger() {
        return trigger;
    }

    public void setTrigger(String trigger) {
        this.trigger = trigger;
    }

    public Reply getReply() {
        return reply;
    }

    public void setReply(Reply reply) {
        this.reply = reply;
    }

    public boolean isLocal() {
        return local;
    }

    public void setLocal(boolean local) {
        this.local = local;
    }
}
