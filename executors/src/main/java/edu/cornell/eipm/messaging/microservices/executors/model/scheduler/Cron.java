package edu.cornell.eipm.messaging.microservices.executors.model.scheduler;

/**
 * Cron Scheduler configuration.
 * @author Manuele Simi
 */
public class Cron {

    private Job job1;
    private Job job2;
    private Job job3;
    private Job job4;
    private Job job5;
    private Job job6;
    private Job job7;
    private Job job8;
    private Job job9;
    private Job job10;
    private Job job11;
    private Job job12;
    private Job job13;
    private Job job14;
    private Job job15;

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

    public void setJob7(Job job) {
        this.job7 = job;
    }

    public void setJob8(Job job) {
        this.job8 = job;
    }

    public void setJob9(Job job) {
        this.job9 = job;
    }

    public void setJob10(Job job) {
        this.job10 = job;
    }

    public void setJob11(Job job) {
        this.job11 = job;
    }

    public void setJob12(Job job) {
        this.job12 = job;
    }

    public void setJob13(Job job) {
        this.job13 = job;
    }

    public void setJob14(Job job) {
        this.job14 = job;
    }

    public void setJob15(Job job) {
        this.job15 = job;
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
            case 7:
                selected= job7;
                break;
            case 8:
                selected= job8;
                break;
            case 9:
                selected= job9;
                break;
            case 10:
                selected= job10;
                break;
            case 11:
                selected= job11;
                break;
            case 12:
                selected= job12;
                break;
            case 13:
                selected= job13;
                break;
            case 14:
                selected= job14;
                break;
            case 15:
                selected= job15;
                break;
        }
        return selected;
    }

}
