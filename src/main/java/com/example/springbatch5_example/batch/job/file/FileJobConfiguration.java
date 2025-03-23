package com.example.springbatch5_example.batch.job.file;

import com.example.springbatch5_example.batch.chunk.processor.FileItemProcessor;
import com.example.springbatch5_example.batch.domain.Product;
import com.example.springbatch5_example.batch.domain.ProductVO;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class FileJobConfiguration {

    @Value("${spring.batch.chunk-size}")
    private int chunkSize;

    private String path = "C:\\Users\\wldns\\IdeaProjects\\SpringBatch5_Example\\src\\main\\resources\\";

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final DataSource dataSource;

    public FileJobConfiguration(JobRepository jobRepository, PlatformTransactionManager transactionManager, DataSource dataSource) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.dataSource = dataSource;
    }

    @Bean
    public Job fileJob() {
        return new JobBuilder("fileJob", jobRepository)
                .start(fileStep())
                .build();
    }

    @Bean
    public Step fileStep() {
        return new StepBuilder("fileStep", jobRepository)
                .<ProductVO, Product>chunk(chunkSize, transactionManager)
                .reader(fileItemReader(null))
                .processor(fileProcessor())
                .writer(fileItemWriter())
                .build();
    }

    @Bean
    @StepScope
    public FlatFileItemReader<ProductVO> fileItemReader(@Value("#{jobParameters['requestDate']}") String requestDate) {
        return new FlatFileItemReaderBuilder<ProductVO>()
                .name("fileItemReader")
                .resource(new FileSystemResource(path + "product_" + requestDate + ".csv"))
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>())
                .targetType(ProductVO.class)
                .linesToSkip(1)
                .delimited().delimiter(",")
                .names("id", "name", "price", "type")
                .build();

    }

    @Bean
    public ItemProcessor<ProductVO, Product> fileProcessor() {
        return new FileItemProcessor();
    }

    @Bean
    public ItemWriter<Product> fileItemWriter() {
        return new JdbcBatchItemWriterBuilder<Product>()
                .dataSource(dataSource)
                .beanMapped()
                .sql("INSERT INTO product (id, name, price, type) VALUES (:id, :name, :price, :type)")
                .assertUpdates(true)
                .build();
    }
}
