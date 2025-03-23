package com.example.springbatch5_example.scheduler;

import org.quartz.*;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static org.quartz.JobBuilder.newJob;

public abstract class JobRunner implements ApplicationRunner {

    private final Scheduler scheduler;

    public JobRunner(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        doRun(args);
    }

    public abstract void doRun(ApplicationArguments args);

    public Trigger buildJobTrigger(String scheduleExp) {
        return TriggerBuilder.newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule(scheduleExp)).build();
    }

    public JobDetail buildJobDetail(Class job, String name, String group, Map params) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.putAll(params);
        return newJob(job).withIdentity(name, group)
                .usingJobData(jobDataMap)
                .build();
    }
}
