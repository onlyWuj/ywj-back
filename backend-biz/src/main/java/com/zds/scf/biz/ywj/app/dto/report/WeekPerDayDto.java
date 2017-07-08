package com.zds.scf.biz.ywj.app.dto.report;

import com.zds.scf.biz.common.dto.BaseDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/19.
 */
public class WeekPerDayDto extends BaseDto {
    Long Mon =0L;
    Long Tue =0L;
    Long Wed =0L;
    Long Thur=0L;
    Long Fri =0L;
    Long Sat =0L;
    Long Sun =0L;

    public Long getMon() {
        return Mon;
    }

    public void setMon(Long mon) {
        Mon = mon;
    }

    public Long getTue() {
        return Tue;
    }

    public void setTue(Long tue) {
        Tue = tue;
    }

    public Long getWed() {
        return Wed;
    }

    public void setWed(Long wed) {
        Wed = wed;
    }

    public Long getThur() {
        return Thur;
    }

    public void setThur(Long thur) {
        Thur = thur;
    }

    public Long getFri() {
        return Fri;
    }

    public void setFri(Long fri) {
        Fri = fri;
    }

    public Long getSat() {
        return Sat;
    }

    public void setSat(Long sat) {
        Sat = sat;
    }

    public Long getSun() {
        return Sun;
    }

    public void setSun(Long sun) {
        Sun = sun;
    }
}


