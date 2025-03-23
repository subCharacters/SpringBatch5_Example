package com.example.springbatch5_example.batch.job.api;

import com.example.springbatch5_example.batch.listener.JobListener;
import com.example.springbatch5_example.batch.tasklet.ApiEndTasklet;
import com.example.springbatch5_example.batch.tasklet.ApiStartTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class ApiJobConfiguration {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final ApiStartTasklet apiStartTasklet;
    private final ApiEndTasklet apiEndTasklet;
    private final Step jobStep;

    public ApiJobConfiguration(JobRepository jobRepository, PlatformTransactionManager transactionManager, ApiStartTasklet apiStartTasklet, ApiEndTasklet apiEndTasklet, Step jobStep) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.apiStartTasklet = apiStartTasklet;
        this.apiEndTasklet = apiEndTasklet;
        this.jobStep = jobStep;
    }

    @Bean
    public Job apiJob() {
        return new JobBuilder("apiJob", jobRepository)
                .listener(new JobListener())
                .start(apiStep1())
                .next(jobStep)
                .next(apiStep2())
                .build();
    }

    @Bean
    public Step apiStep1() {
        return new StepBuilder("apiStep1", jobRepository)
                .tasklet(apiStartTasklet, transactionManager)
                .build();
    }

    @Bean
    public Step apiStep2() {
        return new StepBuilder("apiStep2", jobRepository)
                .tasklet(apiEndTasklet, transactionManager)
                .build();
    }
}
