package com.example.springbatch5_example.scheduler;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class FileJobRunner extends JobRunner {

    private final Scheduler scheduler;

    public FileJobRunner(Scheduler scheduler) {
        super(scheduler);
        this.scheduler = scheduler;
    }

    @Override
    public void doRun(ApplicationArguments args) {
        // 여태까지 프로그램 아규먼츠로 전달했던 값을 여기서 받아서 잡의 전달해줌.
        String[] sourceArgs = args.getSourceArgs();
        JobDetail jobDetail = buildJobDetail(FileSchJob.class, "fileJob", "batch", new HashMap());
        jobDetail.getJobDataMap().put("requestDate", sourceArgs[0]);

        Trigger trigger = buildJobTrigger("0/50 * * * * ?");

        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }
}
