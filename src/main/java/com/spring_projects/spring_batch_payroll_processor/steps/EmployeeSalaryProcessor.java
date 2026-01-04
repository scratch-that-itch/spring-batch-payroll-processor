package com.spring_projects.spring_batch_payroll_processor.steps;

import com.spring_projects.spring_batch_payroll_processor.models.EmployeeWorklog;
import com.spring_projects.spring_batch_payroll_processor.models.PayrollRecord;
import org.jspecify.annotations.Nullable;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class EmployeeSalaryProcessor implements ItemProcessor<EmployeeWorklog, PayrollRecord> {
    @Override
    public @Nullable PayrollRecord process(EmployeeWorklog item) throws Exception {
        PayrollRecord payrollRecord = new PayrollRecord();
        payrollRecord.setEmpId(item.getEmpId());
        payrollRecord.setName(item.getName());
        // Calculate salary and bonus
        double salary = item.getDaysWorked() * item.getPerDaySalary();
        double bonus = salary > 10000 ? salary * 0.1 : 0;
        payrollRecord.setSalary(salary);
        payrollRecord.setBonus(bonus);
        return payrollRecord;
    }
}
