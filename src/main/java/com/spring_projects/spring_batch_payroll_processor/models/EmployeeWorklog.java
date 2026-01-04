package com.spring_projects.spring_batch_payroll_processor.models;

public class EmployeeWorklog {
    private String empId;
    private String name;
    private int daysWorked;
    private double perDaySalary;
    
    public String getEmpId() {
        return empId;
    }
    
    public void setEmpId(String empId) {
        this.empId = empId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getDaysWorked() {
        return daysWorked;
    }
    
    public void setDaysWorked(int daysWorked) {
        this.daysWorked = daysWorked;
    }
    
    public double getPerDaySalary() {
        return perDaySalary;
    }
    
    public void setPerDaySalary(double perDaySalary) {
        this.perDaySalary = perDaySalary;
    }
}
