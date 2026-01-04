-- Schema for a test table used during development
CREATE TABLE IF NOT EXISTS EMPLOYEE_WORKLOG (
  ID BIGINT AUTO_INCREMENT PRIMARY KEY,
  emp_id VARCHAR(10),
  name VARCHAR(50),
  days_worked INTEGER,
  per_day_salary INTEGER
);
CREATE TABLE IF NOT EXISTS PAYROLL (
    emp_id VARCHAR(10),
    name VARCHAR(50),
    salary DOUBLE,
    bonus DOUBLE
);

