package com.spring_projects.spring_batch_payroll_processor;

import com.spring_projects.spring_batch_payroll_processor.models.EmployeeWorklog;
import com.spring_projects.spring_batch_payroll_processor.models.PayrollRecord;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class PayrollJobConfig {
    @Bean
    public Job payrollJob(JobRepository jobRepository, Step payrollStep) {
        return new JobBuilder("payrollJob", jobRepository)
                .start(payrollStep)
                .build();
    }

    @Bean
    public Step payrollStep(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                            ItemReader<EmployeeWorklog> employeeWorklogItemReader,
                            ItemProcessor<EmployeeWorklog, PayrollRecord> employeeSalaryProcessor,
                            ItemWriter<PayrollRecord> employeePayrollItemWriter) {
        return new StepBuilder("payrollStep", jobRepository)
                .<EmployeeWorklog, PayrollRecord>chunk(3)
                .transactionManager(transactionManager)
                .reader(employeeWorklogItemReader)
                .processor(employeeSalaryProcessor)
                .writer(employeePayrollItemWriter)
                .build();
    }
}
