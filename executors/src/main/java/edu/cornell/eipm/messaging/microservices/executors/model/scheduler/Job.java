package edu.cornell.eipm.messaging.microservices.executors.model.scheduler;

import edu.cornell.eipm.messaging.microservices.executors.model.service.Action;

import java.util.ArrayList;
import java.util.List;

/**
 * The job configuration.
 *
 * @author Manuele Simi
 */
public class Job {

    String when;

    public String getWhen() {
        return when;
    }

    public void setWhen(String when) {
        this.when = when;
    }

    private final List<Action> actions = new ArrayList<>();

    public List<Action> getActions() {
        return actions;
    }
}
