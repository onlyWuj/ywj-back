package com.zds.scf.biz.ywj.app.dto.report;

import com.zds.scf.biz.common.dto.base.BaseDto;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/19.
 */
public class DayPerHourDto extends BaseDto {
   Map<String,Long> hours ;
   Map<String,Long> hourPeriod;

    public DayPerHourDto(){
       hours = new HashMap();
       for(int i =0 ; i<24 ;i++){
           hours.put(String.valueOf(i),0L);
       }
        hourPeriod = new HashMap();
   }

    public Map<String, Long> getHours() {
        return hours;
    }

    public void setHours(Map<String, Long> hours) {
        this.hours = hours;
    }

    public void sumHourPeriod(int startHour, int endHour) {
        String period = startHour+"-"+endHour;
        if(hourPeriod.containsKey(period)){
            return;
        }
        long sum = 0;
        for(int i = startHour ; i<endHour ;i++){
            try {
                sum+=hours.get(String.valueOf(i));
            }catch (NullPointerException e){
                e.printStackTrace();
            }

        }
        hourPeriod.put(period,sum);
    }

    public Map<String, Long> getHourPeriod() {
        return hourPeriod;
    }

    public void setHourPeriod(Map<String, Long> hourPeriod) {
        this.hourPeriod = hourPeriod;
    }
}




