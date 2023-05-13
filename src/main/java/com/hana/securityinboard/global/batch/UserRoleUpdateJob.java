package com.hana.securityinboard.global.batch;

import com.hana.securityinboard.application.domain.UserAccount;
import com.hana.securityinboard.application.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@RequiredArgsConstructor
public class UserRoleUpdateJob {

    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    @Bean
    public Job userRoleJob(Step trMigrationStep) {
        return new JobBuilder("userRoleJob")
                .repository(jobRepository)
                .start(trMigrationStep)
                .build();
    }

    @JobScope
    @Bean
    public Step userRoleStep(ItemReader readeUsers, ItemProcessor userProcessor, ItemWriter userWriter) {
        return new StepBuilder("userRoleStep")
                .<UserAccount, UserAccount>chunk(5)
                .reader(readeUsers)
                .processor(userProcessor)
                .writer(userWriter)
                .build();
    }



    @StepScope
    @Bean
    public RepositoryItemWriter<UserAccount> userWriter() {
        return new RepositoryItemWriterBuilder<UserAccount>()
                .repository(userRepository)
                .methodName("findAll")
                .build();

    }

    @StepScope
    @Bean
    public ItemProcessor<UserAccount, UserAccount> userProcessor() {
        return new ItemProcessor<UserAccount, UserAccount>() {
            @Override
            public UserAccount process(UserAccount item) throws Exception {
                item.UpgradeValidation(item);
                return item;
            }
        };
    }


    @StepScope
    @Bean
    public RepositoryItemReader<UserAccount> readeUsers() {
        return new RepositoryItemReaderBuilder<UserAccount>()
                .name("readeUsers")
                .repository(userRepository)
                .methodName("findAll")
                .pageSize(5)
                .arguments(Arrays.asList())
                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
                .build();
    }

}
