package com.zds.scf.biz.ywj.domain.entity.report;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/21.
 */
public class HourLog implements Serializable {
    private String operation;
    private Integer hour;
    private Long counter;

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Long getCounter() {
        return counter;
    }

    public void setCounter(Long counter) {
        this.counter = counter;
    }
}
