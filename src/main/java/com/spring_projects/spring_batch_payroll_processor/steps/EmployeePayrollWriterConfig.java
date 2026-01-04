package com.spring_projects.spring_batch_payroll_processor.steps;

import com.spring_projects.spring_batch_payroll_processor.models.PayrollRecord;
import org.springframework.batch.infrastructure.item.database.JdbcBatchItemWriter;
import org.springframework.batch.infrastructure.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class EmployeePayrollWriterConfig {
    private final String INSERT_QUERY = """
                INSERT INTO payroll (emp_id, name, salary, bonus)
                VALUES (:empId, :name, :salary, :bonus)
            """;
    @Bean
    public JdbcBatchItemWriter<PayrollRecord> payrollBatchItemWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<PayrollRecord>()
                .dataSource(dataSource)
                .sql(INSERT_QUERY)
                .beanMapped()
                .build();
    }
}
