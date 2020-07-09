# Scheduling Jobs
Kafka-Dispatcher provides support for task scheduling and execution.
 
You can use the configurations fixed/delay/cron to provide the triggering information. Where:

1. <code>fixed</code> makes KD run the task on periodic intervals even if the last invocation may be still running.
2. <code>delay</code> specifically controls the next execution time when the last execution finishes.
3. <code>cron</code> is a feature originating from Unix [cron](https://en.wikipedia.org/wiki/Cron) utility and has various options based on your requirements

All the 3 types of schedulers can work at the same time if jobs are configured in the scheduler's section of the configuration (see below).

## Enabling the Scheduler 
To enable the support for scheduling tasks, the `schedulers.enable` property must be set to true.
 
## Activating the jobs
Each job can be individually enabled/disabled by setting the `active` property of the job to true or false. 

## Configuration
### Fixed Scheduler 
Sample configuration:

````yaml
  schedulers:
    enable: true
    fixed:
      job1:
        active: true
        when: 5000
        actions:
          - trigger: >
              https://dzone.com/articles/kafka-cluster-1
          - trigger: echo "hello"
      job2:
        active: true
        when: 10000
        actions:
          - trigger: http://google.com
          - trigger: echo "hello from job2"
      job3:
        active: true
        when: 15000
        actions:
          - trigger: echo "hello from job3"
      job4:
        active: true
        when: 1000
        actions:
          - trigger: echo "hello from job4"
    delay:
    cron:
````
Each job is executed every X time specified in `when`. The interval X is in milliseconds

### Delay Scheduler 
Sample configuration:

````yaml
  schedulers:
    enable: true
    fixed:
    cron:
    delay:
      job1:
        active: false
        when: 5000
        actions:
          - trigger: echo "hello from delay job1"
      job2:
        active: true
        when: 6000
        actions:
          - trigger: echo "hello from delay job2"
````
Each job is executed X time after the previous execution is completed. The interval X between two executions configured in `when` is in milliseconds.

### Cron Scheduler 
Sample configuration:

````yaml
  schedulers:
    enable: true
    fixed:
    cron:
      job1:
        active: true
        when: "*/10 * * * * MON-FRI"
        actions:
          - trigger: echo "hello from cron job1"
    delay:
     
````
The cron expression specified in `when` consists of seven fields:

`second` `minute` `hour` `day-of-month` `month` `day-of-week` `year`

From these, `year` field is optional.
