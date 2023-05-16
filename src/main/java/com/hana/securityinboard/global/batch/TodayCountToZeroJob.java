//package com.hana.securityinboard.global.batch;
//
//import com.hana.securityinboard.application.domain.UserAccount;
//import com.hana.securityinboard.global.util.LoginCounter;
//import com.hana.securityinboard.global.util.LoginCounterRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.JobScope;
//import org.springframework.batch.core.configuration.annotation.StepScope;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.batch.item.ItemReader;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.batch.item.data.RepositoryItemReader;
//import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.domain.Sort;
//
//import java.util.Arrays;
//import java.util.Collections;
//
//@Configuration
//@RequiredArgsConstructor
//public class TodayCountToZeroJob {
//
//    private final JobRepository jobRepository;
//    private final LoginCounterRepository loginCounterRepository;
//
//    @Bean
//    public Job todayCountToZero(Step todayCount) {
//        return new JobBuilder("userRoleJob")
//                .repository(jobRepository)
//                .start(todayCount)
//                .build();
//    }
//
//    @JobScope
//    @Bean
//    public Step todayCount(ItemReader readeUsers, ItemProcessor countProcessor) {
//        return new StepBuilder("userRoleStep")
//                .<LoginCounter, LoginCounter>chunk(5)
//                .reader(readeUsers)
//                .processor(countProcessor)
////                .writer(userWriter)
//                .build();
//    }
//
//
//
//    @Bean
//    public ItemProcessor<LoginCounter, LoginCounter> countProcessor() {
//        return new ItemProcessor<LoginCounter, LoginCounter>() {
//            @Override
//            public LoginCounter process(LoginCounter item) throws Exception {
//                item.setTodayVisit(0);
//                return item;
//            }
//        };
//    }
//
//
//    @StepScope
//    @Bean
//    public RepositoryItemReader<LoginCounter> readeUsers() {
//        return new RepositoryItemReaderBuilder<LoginCounter>()
//                .name("readeUsers")
//                .repository(loginCounterRepository)
//                .methodName("findAll")
//                .pageSize(5)
//                .arguments(Arrays.asList())
//                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
//                .build();
//    }
//
//}
