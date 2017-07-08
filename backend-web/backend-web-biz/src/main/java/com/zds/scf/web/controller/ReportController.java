package com.zds.scf.web.controller;


import com.zds.scf.biz.common.dto.CPResponse;
import com.zds.scf.biz.common.dto.CPSingleValueRequest;
import com.zds.scf.biz.common.dto.CPSingleValueResponse;
import com.zds.scf.biz.common.dto.CPViewResultInfo;
import com.zds.scf.biz.ywj.app.dto.report.*;
import com.zds.scf.biz.ywj.app.dto.user.YWJUserListDto;
import com.zds.scf.biz.ywj.app.service.ReportAppService;
import com.zds.scf.biz.ywj.app.service.YWJUserAppService;
import com.zds.scf.web.AbstractBackendController;
import com.zds.scf.web.common.stereotype.GetMapping;
import com.zds.scf.web.common.stereotype.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 */
@RestController(value = "back_rePortController")
public class ReportController extends AbstractBackendController {
    @Autowired
    private ReportAppService service;

    @GetMapping("/report/allGroup/year/summarize.json")
    public CPViewResultInfo AllGroupYearSummarize() {
        CPSingleValueRequest<String> requestPack = CPSingleValueRequest.from("");
        CPSingleValueResponse<MultiOperationOneYearDto> response = service.AllGroupYearSummarize(requestPack);
        return response.convertTo();
    }

    @GetMapping("/report/login/year/summarize.json")
    public CPViewResultInfo loginYearSummarize() {
        CPSingleValueRequest<String> requestPack = CPSingleValueRequest.from("");
        CPSingleValueResponse<List<OneYearPerDayDto>> response = service.loginYearSummarize(requestPack);
        return response.convertTo();
    }

    @GetMapping("/report/login/weekDay/summarize.json")
    public CPViewResultInfo loginWeekDaySummarize() {
        CPSingleValueRequest<String> requestPack = CPSingleValueRequest.from("");
        CPSingleValueResponse<WeekPerDayDto> response = service.loginWeekDaySummarize(requestPack);
        return response.convertTo();
    }

    @GetMapping("/report/login/hour/summarize.json")
    public CPViewResultInfo loginHourSummarize() {
        CPSingleValueRequest<String> requestPack = CPSingleValueRequest.from("");
        CPSingleValueResponse<DayPerHourDto> response = service.loginHourSummarize(requestPack);
        return response.convertTo();
    }

    @GetMapping("/report/register/year/summarize.json")
    public CPViewResultInfo registerYearSummarize() {
        CPSingleValueRequest<String> requestPack = CPSingleValueRequest.from("");
        CPSingleValueResponse<List<OneYearPerDayDto>> response = service.registerYearSummarize(requestPack);
        return response.convertTo();
    }

    @GetMapping("/report/bind/year/summarize.json")
    public CPViewResultInfo bindYearSummarize() {
        CPSingleValueRequest<String> requestPack = CPSingleValueRequest.from("");
        CPSingleValueResponse<List<OneYearPerDayDto>> response = service.bindYearSummarize(requestPack);
        return response.convertTo();
    }

    @GetMapping("/report/bindGroup/year/summarize.json")
    public CPViewResultInfo bindGroupYearSummarize() {
        CPSingleValueRequest<String> requestPack = CPSingleValueRequest.from("");
        CPSingleValueResponse<MultiOperationOneYearDto> response = service.bindGroupYearSummarize(requestPack);
        return response.convertTo();
    }

    @GetMapping("/report/bind/number/summarize.json")
    public CPViewResultInfo bindNumberSummarize() {
        CPSingleValueRequest<String> requestPack = CPSingleValueRequest.from("");
        CPSingleValueResponse<BindNumberDto> response = service.bindNumberSummarize(requestPack);
        return response.convertTo();
    }

}
