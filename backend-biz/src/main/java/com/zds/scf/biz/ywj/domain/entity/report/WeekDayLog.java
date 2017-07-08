package com.zds.scf.biz.ywj.domain.entity.report;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/21.
 */
public class WeekDayLog implements Serializable {
    private String operation;
    private Integer weekDay;
    private Long counter;

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Integer getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(Integer weekDay) {
        this.weekDay = weekDay;
    }

    public Long getCounter() {
        return counter;
    }

    public void setCounter(Long counter) {
        this.counter = counter;
    }
}
