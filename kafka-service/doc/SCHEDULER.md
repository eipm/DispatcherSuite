## Scheduling Jobs
Kafka-Dispatcher provides support for both task scheduling and asynchronous
 method execution.
 
You can use the configurations fixedDelay/fixed/cron to provide the triggering information. Where:

1. <code>fixed</code> makes Spring run the task on periodic intervals even if the last invocation may be still running.
2. <code>delay</code> specifically controls the next execution time when the last execution finishes.
3. <code>cron</code> is a feature originating from Unix [cron](https://en.wikipedia.org/wiki/Cron) utility and has various options based on your requirements

# Fixed Scheduler
Sample configuration:

````yml
schedulers:
    fixed:
      enable: true
      job1:
        active: true
        rate: 5000
        actions:
          - trigger: >
              https://dzone.com/articles/kafka-cluster-1
          - trigger: echo "hello"
      job2:
        active: true
        rate: 10000
        actions:
          - trigger: http://google.com
          - trigger: echo "hello from job2"
      job3:
        active: true
        rate: 15000
        actions:
          - trigger: echo "hello from job3"
    delay:
      enable: false
    cron:
      enable: false


````