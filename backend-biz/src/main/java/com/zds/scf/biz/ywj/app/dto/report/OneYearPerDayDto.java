package com.zds.scf.biz.ywj.app.dto.report;

import com.zds.scf.biz.common.dto.base.BaseDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/19.
 */
public class OneYearPerDayDto extends BaseDto {
    Integer year;
    List<Long[]> dayCount = new ArrayList();

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public List<Long[]> getDayCount() {
        return dayCount;
    }

    public void setDayCount(List<Long[]> dayCount) {
        this.dayCount = dayCount;
    }
}


