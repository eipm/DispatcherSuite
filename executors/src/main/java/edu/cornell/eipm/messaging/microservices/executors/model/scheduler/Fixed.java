package edu.cornell.eipm.messaging.microservices.executors.model.scheduler;

/**
 * Fixed Scheduler configuration.
 *
 * @author Manuele Simi
 */
public class Fixed {

    private Job job1;
    private Job job2;
    private Job job3;
    private Job job4;
    private Job job5;
    private Job job6;

    public void setJob1(Job job) {
        this.job1 = job;
    }

    public void setJob2(Job job) {
        this.job2 = job;
    }

    public void setJob3(Job job) {
        this.job3 = job;
    }

    public void setJob4(Job job) {
        this.job4 = job;
    }

    public void setJob5(Job job) {
        this.job5 = job;
    }

    public void setJob6(Job job) {
        this.job6 = job;
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
            case 4:
                selected= job4;
                break;
            case 5:
                selected= job5;
                break;
            case 6:
                selected= job6;
                break;
        }
        return selected;
    }
}
