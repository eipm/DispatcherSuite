package edu.cornell.eipm.messaging.microservices.executors.model.scheduler;

import edu.cornell.eipm.messaging.microservices.executors.model.service.Action;

import java.util.ArrayList;
import java.util.List;

/**
 * Fixed Scheduler configuration.
 *
 * @author Manuele Simi
 */
public class Fixed {

    private Job job1;
    private Job job2;
    private Job job3;

    public void setJob1(Job job) {
        this.job1 = job;
    }

    public void setJob2(Job job) {
        this.job2 = job;
    }

    public void setJob3(Job job) {
        this.job3 = job;
    }

    /**
     * Gets the N-th job in the list
     * @param number
     * @return
     */
    public Job getJob(int number) {
        Job selected = null;
        switch (number) {
            case 1:
                selected = job1;
                break;
            case 2:
                selected = job2;
                break;
            case 3:
                selected= job3;
                break;
        }
        return selected;
    }
}
