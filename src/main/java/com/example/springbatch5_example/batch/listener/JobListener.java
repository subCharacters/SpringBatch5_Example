package com.example.springbatch5_example.batch.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

import java.time.Duration;

public class JobListener implements JobExecutionListener {
    @Override
    public void beforeJob(JobExecution jobExecution) {

    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        System.out.println("execute time :" + Duration.between(jobExecution.getStartTime(), jobExecution.getEndTime()));
    }
}
