package com.example.springbatch5_example.scheduler;

import lombok.SneakyThrows;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.*;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FileSchJob extends QuartzJobBean {

    private final Job fileJob;
    private final JobLauncher jobLauncher;
    private final JobExplorer jobExplorer;
    public FileSchJob(Job fileJob, JobLauncher jobLauncher, JobExplorer jobExplorer) {
        this.fileJob = fileJob;
        this.jobLauncher = jobLauncher;
        this.jobExplorer = jobExplorer;
    }

    @SneakyThrows
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        // 배치를 실행시키는 로직을 작성.
        String requestDate = (String) context.getJobDetail().getJobDataMap().get("requestDate");

        long jobInstanceCount = jobExplorer.getJobInstanceCount(fileJob.getName());
        List<JobInstance> jobInstances = jobExplorer.getJobInstances(fileJob.getName(), 0, Integer.parseInt(String.valueOf(jobInstanceCount)));

        if (!jobInstances.isEmpty()) {
            for (JobInstance jobInstance : jobInstances) {
                List<JobExecution> jobExecutions = jobExplorer.getJobExecutions(jobInstance);
                List<JobExecution> jobExecutionList = jobExecutions.stream().filter(jobExecution ->
                        jobExecution.getJobParameters().getString("requestDate").equals(requestDate)).toList();

                if (!jobExecutionList.isEmpty()) {
                    throw new JobExecutionException(requestDate + " already exists");
                }
            }
        }

        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("id", new Date().getTime())
                .addString("requestDate", requestDate)
                .toJobParameters();

        jobLauncher.run(fileJob, jobParameters);
    }
}
