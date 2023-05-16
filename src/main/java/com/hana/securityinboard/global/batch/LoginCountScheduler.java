//package com.hana.securityinboard.global.batch;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.JobParameters;
//import org.springframework.batch.core.JobParametersBuilder;
//import org.springframework.batch.core.JobParametersInvalidException;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
//import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
//import org.springframework.batch.core.repository.JobRestartException;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//@RequiredArgsConstructor
//@Component
//public class LoginCountScheduler {
//
//    private final Job todayCountToZero;
//    private final JobLauncher jobLauncher;
//
//
//    @Scheduled(cron = "0 */1 * * * *")
//    public void loginCount() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
//        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
//        jobParametersBuilder.addString("resultTime", String.valueOf(System.currentTimeMillis()));
//        JobParameters jobParameters = jobParametersBuilder.toJobParameters();
//
//        jobLauncher.run(todayCountToZero, jobParameters);
//    }
//
//
//
//
//}
