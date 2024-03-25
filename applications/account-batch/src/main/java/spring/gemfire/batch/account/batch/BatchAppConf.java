package spring.gemfire.batch.account.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.GemfireTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.PlatformTransactionManager;
import spring.gemfire.showcase.account.domain.account.Account;

import javax.sql.DataSource;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
@EnableTask
@EnableBatchProcessing
@EnableAutoConfiguration
public class BatchAppConf {

    @Value("${batch.read.sql}")
    private String readSql;

    @Value("${batch.read.fetch.size}")
    private int fetchSize;

    @Value("${batch.read.chunk.size}")
    private int chunkSize;


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
    ItemReader<Account> reader(DataSource dataSource)
    {
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
}