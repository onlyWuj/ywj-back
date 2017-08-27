package com.zds.scf.biz.ywj.domain.repository;

import com.zds.scf.biz.ywj.domain.entity.report.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class ReportDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final static String YEAR_MONTH_OPERATIONS ="SELECT OPERATION,YEAR(TIME) YEAR," +
            "SUM(CASE WHEN MONTH(TIME) = 1 THEN 1 ELSE 0 END) JAN," +
            "SUM(CASE WHEN MONTH(TIME) = 2 THEN 1 ELSE 0 END) FEB," +
            "SUM(CASE WHEN MONTH(TIME) = 3 THEN 1 ELSE 0 END) MAR," +
            "SUM(CASE WHEN MONTH(TIME) = 4 THEN 1 ELSE 0 END) APR," +
            "SUM(CASE WHEN MONTH(TIME) = 5 THEN 1 ELSE 0 END) MAY," +
            "SUM(CASE WHEN MONTH(TIME) = 6 THEN 1 ELSE 0 END) JUN," +
            "SUM(CASE WHEN MONTH(TIME) = 7 THEN 1 ELSE 0 END) JUL," +
            "SUM(CASE WHEN MONTH(TIME) = 8 THEN 1 ELSE 0 END) AUG," +
            "SUM(CASE WHEN MONTH(TIME) = 9 THEN 1 ELSE 0 END) SEP," +
            "SUM(CASE WHEN MONTH(TIME) = 10 THEN 1 ELSE 0 END) OCT," +
            "SUM(CASE WHEN MONTH(TIME) = 11 THEN 1 ELSE 0 END) NOV," +
            "SUM(CASE WHEN MONTH(TIME) = 12 THEN 1 ELSE 0 END) _DEC " +
            "FROM SYS_LOG WHERE OPERATION IN (?) " +
            "GROUP BY YEAR(TIME),OPERATION";

    private final static String YEAR_DAY_OPERATION ="SELECT OPERATION,YEAR(TIME) YEAR,MONTH(TIME) MONTH,DAY(TIME) DAY,TIME,SUM(1) COUNTER " +
            "FROM SYS_LOG WHERE OPERATION = '?' GROUP BY YEAR(TIME),MONTH(TIME),DAY(TIME) ORDER BY TIME ASC";

    private final static String WEEKDAY_OPERATION ="SELECT OPERATION, WEEKDAY(TIME) WEEKDAY, SUM(1) COUNTER " +
            "FROM SYS_LOG WHERE OPERATION = '?' GROUP BY WEEKDAY(TIME) ORDER BY TIME ASC";

    private final static String HOUR_OPERATION ="SELECT OPERATION, HOUR(TIME) HOUR, SUM(1) COUNTER " +
            "FROM SYS_LOG WHERE OPERATION = '?' GROUP BY HOUR(TIME) ORDER BY TIME ASC";

    private final static String BIND_NUMBER ="SELECT PHONE_TOTAL BINDNUMBER ,COUNT(1) TOTAL FROM " +
            "(SELECT phone , sum(1) PHONE_TOTAL FROM biz_ywj_device group by phone) TEMP group by PHONE_TOTAL";


    public List<YearDayLog> statsYearDayByOperation(String operation) {
        return jdbcTemplate.query(YEAR_DAY_OPERATION.replace("?",operation),new BeanPropertyRowMapper<>(YearDayLog.class));

    }

    public List<WeekDayLog> statsWeekByOperation(String operation) {
        return jdbcTemplate.query(WEEKDAY_OPERATION.replace("?",operation),new BeanPropertyRowMapper<>(WeekDayLog.class));
    }

    public List<HourLog> statsHourByOperation(String operation) {
        return jdbcTemplate.query(HOUR_OPERATION.replace("?",operation),new BeanPropertyRowMapper<>(HourLog.class));
    }

    public List<YearLog> statsYearDayByOperations(String[] operationStr) {
        StringBuilder sb = new StringBuilder();
        boolean isFrist = true;
        for(String one : operationStr){
            if(!isFrist){
                sb.append(",");
            }else{
                isFrist = false;
            }
            sb.append("'").append(one).append("'");
        }
        return jdbcTemplate.query(YEAR_MONTH_OPERATIONS.replace("?", sb.toString()),new BeanPropertyRowMapper<>(YearLog.class));
    }

    public List<BindNumber> statsBindNumber() {
        return jdbcTemplate.query(BIND_NUMBER,new BeanPropertyRowMapper<>(BindNumber.class));
    }
}
