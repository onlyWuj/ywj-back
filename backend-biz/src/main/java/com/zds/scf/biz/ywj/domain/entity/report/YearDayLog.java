package com.zds.scf.biz.ywj.domain.entity.report;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/6/21.
 */
public class YearDayLog implements Serializable {
    private String operation;
    private Integer year;
    private Integer month;
    private Integer day;
    private Date time;
    private Long counter;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Long getCounter() {
        return counter;
    }

    public void setCounter(Long counter) {
        this.counter = counter;
    }
}
