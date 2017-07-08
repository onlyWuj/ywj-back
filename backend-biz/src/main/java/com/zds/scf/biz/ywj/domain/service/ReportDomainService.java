package com.zds.scf.biz.ywj.domain.service;

import com.cp.boot.appservice.stereotype.DomainService;
import com.zds.scf.biz.common.log.app.service.LogPubService;
import com.zds.scf.biz.ywj.domain.entity.report.*;
import com.zds.scf.biz.ywj.domain.repository.ReportDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@DomainService
public class ReportDomainService {

    @Autowired
    ReportDao DAO;

    public List<YearDayLog> oneOperationYearSummarize(LogPubService.Operation operation) {
        return DAO.statsYearDayByOperation(operation.getOperation());
    }

    public List<WeekDayLog> loginWeekDaySummarize() {
        return DAO.statsWeekByOperation(LogPubService.MOBILE.LOGIN.getOperation());
    }

    public List<HourLog> loginHourSummarize() {
        return DAO.statsHourByOperation(LogPubService.MOBILE.LOGIN.getOperation());
    }


    public List<YearLog> multiOperationYearSummarize(LogPubService.Operation[] operation) {
        String[] operationStr = new String [operation.length];
        for(int i = 0; i <operation.length ; i++){
            operationStr[i] = operation[i].getOperation();
        }
        return DAO.statsYearDayByOperations(operationStr);
    }

    public List<BindNumber> bindNumberSummarize() {
        return DAO.statsBindNumber();
    }
}