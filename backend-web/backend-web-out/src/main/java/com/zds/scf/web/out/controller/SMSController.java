package com.zds.scf.web.out.controller;

import com.zds.scf.biz.common.dto.SMSNoticeDto;
import com.zds.scf.biz.common.dto.base.CPSingleValueRequest;
import com.zds.scf.biz.common.dto.base.CPViewResultInfo;
import com.zds.scf.biz.ywj.app.service.SMSAppService;
import com.zds.scf.biz.ywj.app.service.YWJUserAppService;
import com.zds.scf.web.common.stereotype.GetMapping;
import com.zds.scf.web.common.stereotype.PostMapping;
import com.zds.scf.web.out.AbstractOutController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017/8/23.
 */
@RestController()
public class SMSController  extends AbstractOutController {

    @Autowired
    private SMSAppService service;

    @GetMapping("/getVerifyCode.json")
    public CPViewResultInfo getVerifyCode(HttpServletRequest request, String phone) {
        String params[] = {phone,request.getRemoteAddr()};
        CPSingleValueRequest<String[]> requestPack = CPSingleValueRequest.from(params);
        return  service.getVerifyCode(requestPack).convertTo();
    }

    @PostMapping("/sendNotice.json")
    public CPViewResultInfo sendNotice(SMSNoticeDto dto) {
        CPSingleValueRequest<SMSNoticeDto> requestPack = CPSingleValueRequest.from(dto);
        return  service.sendNoticeMsg(requestPack).convertTo();
    }
}

