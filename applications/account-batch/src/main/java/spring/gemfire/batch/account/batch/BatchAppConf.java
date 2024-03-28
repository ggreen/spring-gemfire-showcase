package spring.gemfire.batch.account.batch;

import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.gemfire.GemfireTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import spring.gemfire.showcase.account.domain.account.Account;

import java.util.Map;
import java.util.UUID;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
@EnableTask
@EnableTransactionManagement
//${batch.job.repository.schema.prefix:}BOOT3_BATCH_
@EnableBatchProcessing(tablePrefix = "${batch.job.repository.schema.prefix:}BOOT3_BATCH_")
@EnableAutoConfiguration
public class BatchAppConf {


    @Value("${batch.read.sql}")
    private String readSql;

    @Value("${batch.read.fetch.size}")
    private int fetchSize;

    @Value("${batch.read.chunk.size}")
    private int chunkSize;

    @Value("${batch.jdbc.url}")
    private String batchJdbcUrl;

    @Value("${batch.jdbc.username}")
    private String batchUsername;


    @Value("${batch.jdbc.password:''}")
    private String batchPassword;


    @Bean
    public JobLauncher jobLauncher(JobRepository jobRepository) throws Exception {
        TaskExecutorJobLauncher jobLauncher = new TaskExecutorJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.afterPropertiesSet();
        return jobLauncher;
    }

    @Bean
    Job job(JobRepository jobRepository, Step step) {
        return new JobBuilder("account-job", jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(step)
                .end()
                .build();
    }

    @Bean
    ItemReader<Account> reader()
    {
        var dataSource = DataSourceBuilder.create().
        url(batchJdbcUrl).username(batchUsername)
                .password(batchPassword).build();

        RowMapper<Account> rowMapper = (rs,i) ->   Account.builder()
                    .id(rs.getString(1))
                    .name(rs.getString(2))
                    .build();

        return new JdbcCursorItemReaderBuilder<Account>()
                .dataSource(dataSource)
                .name("accounts")
                .rowMapper(rowMapper)
                .sql(readSql)
                .fetchSize(fetchSize)
                .build();
    }

    @Bean
    ItemWriter<Account> writer(GemfireTemplate gemFireTemplate){

        ItemWriter<Account> itemWriter = c ->
            gemFireTemplate.putAll(convertToMap(c));

        return itemWriter;
    }

    protected BinaryOperator<Account> mergeFunction() {
        return (oldValue, newValue) -> {
            System.out.println(String.format("Duplicate key %s", oldValue));
            return newValue;
        };
    }

    private Map<?,?> convertToMap(Chunk<? extends Account> chunk) {

        Function<Account,String> toKeyFunction = account -> account.getId();

        return  chunk.getItems().parallelStream().collect(
                Collectors.toMap(toKeyFunction, i -> i, mergeFunction()));


    }

    @Bean
    public Step sampleStep(JobRepository jobRepository,
                           PlatformTransactionManager transactionManager,
                           ItemReader<Account> itemReader,
                           ItemWriter<Account> itemWriter) {
        return new StepBuilder("load-step", jobRepository)
                .<Account, Account>chunk(chunkSize, transactionManager)
                .reader(itemReader)
                .writer(itemWriter)
                .build();
    }

    @Bean
    @Order(10)
    CommandLineRunner runner(Job job,JobLauncher jobLauncher) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        return args -> {
            String jobId = UUID.randomUUID().toString();
            JobParameter<?> jobIdParam = new JobParameter<String>(jobId, String.class);
            JobParameters jobParameters = new JobParameters(Map.of("jobId", jobIdParam));
            jobLauncher.run(job, jobParameters);
        };
    }
}
