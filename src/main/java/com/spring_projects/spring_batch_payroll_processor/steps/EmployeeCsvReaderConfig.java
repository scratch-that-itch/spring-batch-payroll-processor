package com.spring_projects.spring_batch_payroll_processor.steps;

import com.spring_projects.spring_batch_payroll_processor.models.EmployeeWorklog;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.infrastructure.item.file.mapping.FieldSetMapper;
import org.springframework.batch.infrastructure.item.file.transform.FieldSet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class EmployeeCsvReaderConfig {
    @Bean
    public FlatFileItemReader<EmployeeWorklog> employeeReader() {
        FieldSetMapper<EmployeeWorklog> safeMapper = new FieldSetMapper<EmployeeWorklog>() {
            @Override
            public EmployeeWorklog mapFieldSet(FieldSet fs) {
                EmployeeWorklog e = new EmployeeWorklog();
                // read as strings and trim to avoid header/raw whitespace
                String empId = fs.readString("empId");
                String name = fs.readString("name");
                String days = fs.readString("daysWorked");
                String perDay = fs.readString("perDaySalary");

                e.setEmpId(empId != null ? empId.trim() : null);
                e.setName(name != null ? name.trim() : null);

                try {
                    e.setDaysWorked(days != null && !days.trim().isEmpty() ? Integer.parseInt(days.trim()) : 0);
                } catch (NumberFormatException ex) {
                    e.setDaysWorked(0);
                }

                try {
                    e.setPerDaySalary(perDay != null && !perDay.trim().isEmpty() ? Double.parseDouble(perDay.trim()) : 0.0);
                } catch (NumberFormatException ex) {
                    e.setPerDaySalary(0.0);
                }

                return e;
            }
        };

        return new FlatFileItemReaderBuilder<EmployeeWorklog>()
                .name("employeeCsvItemReader")
                .resource(new ClassPathResource("employee_worklog.csv"))
                .linesToSkip(1) // skip header row
                .delimited()
                .names("empId", "name", "daysWorked", "perDaySalary")
                .fieldSetMapper(safeMapper)
                .build();
    }
}
