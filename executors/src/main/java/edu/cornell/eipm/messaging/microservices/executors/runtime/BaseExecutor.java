package edu.cornell.eipm.messaging.microservices.executors.runtime;

import edu.cornell.eipm.messaging.microservices.executors.model.service.Action;

import java.io.IOException;
import java.util.Map;

/**
 * Base logic common to all {@link Executor}s
 * @author Manuele Simi
 */
public abstract class BaseExecutor implements Executor {

    protected Action action;

    @Override
    public void setAction(Action action) {
        this.action = action;
    }

    @Override
    public boolean execute(Map<String, String> values, boolean local, MODE mode) throws IOException {
        String actualTrigger = new TriggerValue(action.getTrigger()).getActualValue(values);
        return this.run(actualTrigger, local, mode);
    }

    /**
     * Runs the trigger with its actual values
     * @param actualTrigger
     * @param local
     * @param mode
     * @return
     * @throws IOException
     */
    protected abstract boolean run(String actualTrigger, boolean local, MODE mode) throws IOException;

}
