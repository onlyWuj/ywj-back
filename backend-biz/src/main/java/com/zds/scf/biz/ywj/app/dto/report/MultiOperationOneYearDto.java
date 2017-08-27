package com.zds.scf.biz.ywj.app.dto.report;

import com.zds.scf.biz.common.dto.base.BaseDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/19.
 */
public class MultiOperationOneYearDto extends BaseDto {
    private List<OneYear> years;

    public MultiOperationOneYearDto(){
        years = new ArrayList();
    }

    public List<OneYear> getYears() {
        return years;
    }

    public void setOneYearOneOperation(int year , String operation , List<Integer> monthCounter){
        OneYear oneYear = null ;
        for(OneYear one : years){
            if(one.getYear().equals(year)){
                oneYear = one;
            }
        }
        if(oneYear == null){
            oneYear = new OneYear(year);
            years.add(oneYear);
        }
        oneYear.setOneOperationPerMonth(operation,monthCounter);
    }

    private class OneYear{
        private Integer year ;
        private List<OneOperationPerMonth> oneOperationPerMonths;

        public OneYear(int year){
            this.year = year;
            this.oneOperationPerMonths = new ArrayList();
        }

        public Integer getYear() {
            return year;
        }

        public List<OneOperationPerMonth> getOneOperationPerMonths() {
            return oneOperationPerMonths;
        }

        public void setOneOperationPerMonth(String operation, List<Integer> counters) {
            OneOperationPerMonth oneOperationPerMonth = null ;
            for(OneOperationPerMonth one : oneOperationPerMonths){
                if(one.getOperation().equals(operation)){
                    oneOperationPerMonth = one;
                }
            }
            if(oneOperationPerMonth == null){
                oneOperationPerMonth = new OneOperationPerMonth(operation,counters);
                oneOperationPerMonths.add(oneOperationPerMonth);
            }
        }


    }

    private class OneOperationPerMonth {
        private String operation;
        private List<Integer> monthCounter;

        public OneOperationPerMonth(String operation ,List<Integer>monthCounter){
            this.operation = operation;
            this.monthCounter = monthCounter;
        }

        public String getOperation() {
            return operation;
        }

        public List<Integer> getMonthCounter() {
            return monthCounter;
        }
    }
}


