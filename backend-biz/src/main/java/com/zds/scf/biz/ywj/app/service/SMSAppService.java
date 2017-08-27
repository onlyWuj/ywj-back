package com.zds.scf.biz.ywj.app.service;

import com.cp.boot.appservice.AppService;
import com.zds.common.lang.exception.BusinessException;
import com.zds.scf.biz.common.CPBusinessException;
import com.zds.scf.biz.common.dto.SMSNoticeDto;
import com.zds.scf.biz.common.dto.SMSVerifyDto;
import com.zds.scf.biz.common.dto.base.CPResponse;
import com.zds.scf.biz.common.dto.base.CPSingleValueRequest;
import com.zds.scf.biz.common.dto.base.CPSingleValueResponse;
import com.zds.scf.biz.common.log.app.service.LogPubService;
import com.zds.scf.biz.common.util.Pages;
import com.zds.scf.biz.ywj.app.dto.user.*;
import com.zds.scf.biz.ywj.app.service.sms.VerifyService;
import com.zds.scf.biz.ywj.domain.entity.YWJUser;
import com.zds.scf.biz.ywj.domain.entity.device.YWJDevice;
import com.zds.scf.biz.ywj.domain.service.YWJDeviceDomainService;
import com.zds.scf.biz.ywj.domain.service.YWJUserDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@AppService
@Transactional
public class SMSAppService {

    @Autowired
    private VerifyService verifyService;

    @Autowired
    private YWJDeviceDomainService deviceDomainService;

    public CPResponse getVerifyCode(CPSingleValueRequest<String[]> requestPack) {
        SMSVerifyDto dto = verifyService.getVerifyCode(requestPack.getData()[0],requestPack.getData()[1]);
        return CPSingleValueResponse.from(dto);
    }

    public CPResponse sendNoticeMsg(CPSingleValueRequest<SMSNoticeDto> requestPack) {
        SMSNoticeDto dto = requestPack.getData();
        List<YWJDevice> devices = deviceDomainService.loadByDeviceId(dto.getDevice());
        if(devices.size()<1){
            CPBusinessException.throwIt("设备"+dto.getDevice()+"没有绑定手机用户!");
        }
        List<String> phones = new ArrayList();
        for (YWJDevice device :devices){
            phones.add(device.getPhone());
        }
        List<String> params = Arrays.asList(dto.getParams().split(","));
        verifyService.sendNoticeMsg(phones,dto.getTemplateid(),params);
        return CPResponse.create();
    }


}
