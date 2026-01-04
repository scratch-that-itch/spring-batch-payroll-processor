package com.spring_projects.spring_batch_payroll_processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class DatabaseInspector {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final JdbcTemplate jdbcTemplate;

    public DatabaseInspector(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void inspectBatchTables() {
        log.info("Inspecting Spring Batch tables in the H2 database...");
        String[] tables = new String[] {"BATCH_JOB_INSTANCE", "BATCH_JOB_EXECUTION", "BATCH_STEP_EXECUTION", "BATCH_JOB_EXECUTION_PARAMS", "EMPLOYEE_WORKLOG", "PAYROLL"};
        for (String table : tables) {
            try {
                // Check existence
                List<Map<String,Object>> exists = jdbcTemplate.queryForList(
                        "SELECT TABLE_SCHEMA, TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = ?", table);
                if (exists.isEmpty()) {
                    log.info("Table {} does not exist in the current database.", table);
                    continue;
                }
                log.info("Table {} exists. Querying up to 50 rows...", table);
                List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM " + table + " LIMIT 50");
                if (rows.isEmpty()) {
                    log.info("{}: no rows", table);
                } else {
                    log.info("{} rows (showing up to 50):", table);
                    for (Map<String, Object> row : rows) {
                        log.info("  {}", row);
                    }
                }
            } catch (Exception ex) {
                log.warn("Unable to query table {}: {}", table, ex.toString());
            }
        }
    }
}
