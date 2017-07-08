package com.zds.scf.biz.ywj.app.service;

import com.cp.boot.appservice.AppService;
import com.zds.scf.biz.common.dto.CPSingleValueRequest;
import com.zds.scf.biz.common.dto.CPSingleValueResponse;
import com.zds.scf.biz.common.log.app.service.LogPubService;
import com.zds.scf.biz.ywj.app.dto.report.*;
import com.zds.scf.biz.ywj.domain.entity.report.*;
import com.zds.scf.biz.ywj.domain.service.ReportDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by Administrator on 2017/6/19.
 */

@AppService
@Transactional
public class ReportAppService {

    @Autowired
    ReportDomainService domainService;
    @Transactional(readOnly = true)
    public CPSingleValueResponse<MultiOperationOneYearDto> AllGroupYearSummarize(CPSingleValueRequest<String> requestPack) {
        MultiOperationOneYearDto dto = this.multiOperationYearSummarize(LogPubService.MOBILE.LOGIN,LogPubService.MOBILE.REGISTER,LogPubService.MOBILE.BIND);
        return CPSingleValueResponse.from(dto);
    }

    @Transactional(readOnly = true)
    public CPSingleValueResponse<MultiOperationOneYearDto> bindGroupYearSummarize(CPSingleValueRequest<String> requestPack) {
        MultiOperationOneYearDto dto = this.multiOperationYearSummarize(LogPubService.MOBILE.BIND,LogPubService.MOBILE.CHANGE_BIND,LogPubService.MOBILE.UNBIND);
        return CPSingleValueResponse.from(dto);
    }

    @Transactional(readOnly = true)
    public CPSingleValueResponse<List<OneYearPerDayDto>> loginYearSummarize(CPSingleValueRequest<String> requestPack) {
        List<OneYearPerDayDto> dto = this.oneOperationYearSummarize(LogPubService.MOBILE.LOGIN);
        return CPSingleValueResponse.from(dto);
    }

    @Transactional(readOnly = true)
    public CPSingleValueResponse<List<OneYearPerDayDto>> bindYearSummarize(CPSingleValueRequest<String> requestPack) {
        List<OneYearPerDayDto> dto = this.oneOperationYearSummarize(LogPubService.MOBILE.BIND);
        return CPSingleValueResponse.from(dto);
    }

    @Transactional(readOnly = true)
    public CPSingleValueResponse<List<OneYearPerDayDto>> registerYearSummarize(CPSingleValueRequest<String> requestPack) {
        List<OneYearPerDayDto> dto = this.oneOperationYearSummarize(LogPubService.MOBILE.REGISTER);
        return CPSingleValueResponse.from(dto);
    }

    @Transactional(readOnly = true)
    public CPSingleValueResponse<WeekPerDayDto> loginWeekDaySummarize(CPSingleValueRequest<String> requestPack) {
        List<WeekDayLog> weekLogs = domainService.loginWeekDaySummarize();
        WeekPerDayDto dto = new WeekPerDayDto();
        for(WeekDayLog log : weekLogs){
            switch (log.getWeekDay()){
                case 0 : dto.setMon(log.getCounter());break;
                case 1 : dto.setTue(log.getCounter());break;
                case 2 : dto.setWed(log.getCounter());break;
                case 3 : dto.setThur(log.getCounter());break;
                case 4 : dto.setFri(log.getCounter());break;
                case 5 : dto.setSat(log.getCounter());break;
                case 6 : dto.setSun(log.getCounter());break;
            }
        }
        return CPSingleValueResponse.from(dto);
    }

    @Transactional(readOnly = true)
    public CPSingleValueResponse<DayPerHourDto> loginHourSummarize(CPSingleValueRequest<String> requestPack) {
        List<HourLog> hourLogs = domainService.loginHourSummarize();
        DayPerHourDto dto = new DayPerHourDto();
        for(HourLog log : hourLogs){
            dto.getHours().put(String.valueOf(log.getHour()),log.getCounter());
        }
        dto.sumHourPeriod(0,6);
        dto.sumHourPeriod(6,9);
        dto.sumHourPeriod(9,12);
        dto.sumHourPeriod(12,15);
        dto.sumHourPeriod(15,18);
        dto.sumHourPeriod(18,21);
        dto.sumHourPeriod(21,24);
        return CPSingleValueResponse.from(dto);
    }

    @Transactional(readOnly = true)
    public CPSingleValueResponse<BindNumberDto> bindNumberSummarize(CPSingleValueRequest<String> requestPack) {
        List<BindNumber> bindNumbers = domainService.bindNumberSummarize();
        BindNumberDto dto = new BindNumberDto();
        for(BindNumber one : bindNumbers){
            switch (one.getBindNumber()){
                case 1:dto.setOne(one.getTotal());break;
                case 2:dto.setTwo(one.getTotal());break;
                case 3:dto.setThree(one.getTotal());break;
                case 4:dto.setFour(one.getTotal());break;
                case 5:dto.setFive(one.getTotal());break;
                default: dto.setThFive(dto.getThFive()+one.getTotal()); //大于5的在这统计
            }
        }
        return CPSingleValueResponse.from(dto);
    }

    private List<OneYearPerDayDto> oneOperationYearSummarize(LogPubService.Operation operation) {
        List<YearDayLog> yearLogs = domainService.oneOperationYearSummarize(operation);
        Map<Integer, OneYearPerDayDto> temp = new HashMap();
        for(YearDayLog log : yearLogs){
            OneYearPerDayDto yearPerDayDto = temp.get(log.getYear());
            if(yearPerDayDto==null){
                yearPerDayDto = new OneYearPerDayDto();
                yearPerDayDto.setYear(log.getYear());
                temp.put(log.getYear(),yearPerDayDto);
            }
            Long[] dayConut ={log.getTime().getTime(),log.getCounter()};
            yearPerDayDto.getDayCount().add(dayConut);
        }
        List<OneYearPerDayDto> dto = new ArrayList();
        dto.addAll(temp.values());
        return dto;
    }

    private MultiOperationOneYearDto multiOperationYearSummarize(LogPubService.Operation... operation) {
        List<YearLog> yearLogs = domainService.multiOperationYearSummarize(operation);
        MultiOperationOneYearDto dto = new MultiOperationOneYearDto();
        for (YearLog log : yearLogs) {
            List monthCounter = new ArrayList();
            monthCounter.add(log.getJAN());
            monthCounter.add(log.getFEB());
            monthCounter.add(log.getMAR());
            monthCounter.add(log.getAPR());
            monthCounter.add(log.getMAY());
            monthCounter.add(log.getJUN());
            monthCounter.add(log.getJUL());
            monthCounter.add(log.getAUG());
            monthCounter.add(log.getSEP());
            monthCounter.add(log.getOCT());
            monthCounter.add(log.getNOV());
            monthCounter.add(log.get_DEC());
            dto.setOneYearOneOperation(log.getYear(),log.getOperation(),monthCounter);
        }
        return dto;
    }

}
