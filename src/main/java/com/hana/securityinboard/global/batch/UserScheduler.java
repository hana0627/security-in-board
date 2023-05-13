package com.hana.securityinboard.global.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class UserScheduler {

    private final Job userRoleUpdateJob;
    private final JobLauncher jobLauncher;


    @Scheduled(cron = "0 */1 * * * *")
    public void updateUserRole() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        jobParametersBuilder.addString("resultTime", String.valueOf(System.currentTimeMillis()));
        JobParameters jobParameters = jobParametersBuilder.toJobParameters();

        jobLauncher.run(userRoleUpdateJob, jobParameters);
    }
}