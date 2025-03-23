package com.example.springbatch5_example.scheduler;

import lombok.SneakyThrows;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ApiSchJob extends QuartzJobBean {

    private final Job apiJob;
    private final JobLauncher jobLauncher;
    public ApiSchJob(Job apiJob, JobLauncher jobLauncher) {
        this.apiJob = apiJob;
        this.jobLauncher = jobLauncher;
    }

    @SneakyThrows
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        // 배치를 실행시키는 로직을 작성.
        JobParameters jobParameters = new JobParametersBuilder().addLong("id", new Date().getTime()).toJobParameters();
        jobLauncher.run(apiJob, jobParameters);
    }
}
