package edu.cornell.eipm.messaging.microservices.executors.runtime;

import edu.cornell.eipm.messaging.microservices.executors.model.service.Action;

import java.io.IOException;
import java.util.Map;

/**
 * Interface for all Executors.
 * @author Manuele Simi
 */
public interface Executor {

    void setAction(Action action);

    /**
     * Executes the action.
     * @param values actual parameters for the execution
     * @param local forces the action to be locally executed
     * @param mode execution mode
     * @return
     * @throws IOException
     */
    boolean execute( Map<String,String> values, boolean local, MODE mode) throws IOException;
}
