package com.zds.scf.biz.ywj.app.service;

import com.alibaba.fastjson.JSONObject;
import com.cp.boot.appservice.AppService;
import com.zds.scf.biz.common.CPBusinessException;
import com.zds.scf.biz.common.dto.CPResponse;
import com.zds.scf.biz.common.dto.CPSingleValueRequest;
import com.zds.scf.biz.common.dto.CPSingleValueResponse;
import com.zds.scf.biz.common.log.app.service.LogPubService;
import com.zds.scf.biz.common.util.Pages;
import com.zds.scf.biz.ywj.app.dto.user.*;
import com.zds.scf.biz.ywj.app.service.sms.SendClient;
import com.zds.scf.biz.ywj.domain.entity.YWJUser;
import com.zds.scf.biz.ywj.domain.service.YWJUserDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@AppService
@Transactional
public class YWJUserAppService {

    static int effectiveTime = 60*10;

    @Autowired
    private YWJUserDomainService domainService;

    @Autowired
    private LogPubService logService;

    @Autowired
    SendClient sendClient;

    private Map<String,SMSHolder> verifyCodeMap = new ConcurrentHashMap();

    public CPSingleValueResponse<Long> register(CPSingleValueRequest<YWJUserCreateDto> request) {
        YWJUserCreateDto dto = request.getData();
        SMSHolder smsHolder =  verifyCodeMap.get(dto.getPhone());
        if(smsHolder == null){
            CPBusinessException.throwIt("请先获取手机验证码!");
        }
        if(!smsHolder.getCode().equals(dto.getVerifyCode())){
            CPBusinessException.throwIt("验证码输入不正确!");
        }
        if( System.currentTimeMillis()-smsHolder.getTime()>effectiveTime*1000){
            CPBusinessException.throwIt("验证码已过期，请重新获取!");
        }
        Long id = domainService.create(dto).getId();
        logService.log(dto.getPhone(),LogPubService.MOBILE.REGISTER);
        return CPSingleValueResponse.from(id );
    }

    public CPSingleValueResponse<YWJUserVerifyDto> verify(CPSingleValueRequest<String> requestPack) {
        YWJUser user = domainService.loadByPhone(requestPack.getData());
        YWJUserVerifyDto dto = new YWJUserVerifyDto();
        dto.setPhone(requestPack.getData());
        if(user!=null){
            dto.setResult(false);
            return  CPSingleValueResponse.from(dto);
        }else{
            dto.setResult(true);
            String smsString = sendClient.sendSms(requestPack.getData());
            JSONObject json =  JSONObject.parseObject(smsString);
            if((Integer)json.get("code")!=200){
                CPBusinessException.throwIt(String.valueOf(json.get("msg")));
            }
            String correctVerify =  String.valueOf(json.get("obj"));
            dto.setVerifyCode(correctVerify);
            verifyCodeMap.put(requestPack.getData(),new SMSHolder(correctVerify));
            dto.setEffective(effectiveTime);
            return  CPSingleValueResponse.from(dto);
        }
    }


    public CPResponse login(CPSingleValueRequest<YWJUserLoginDto> request) {
        domainService.login(request.getData());
        logService.log(request.getData().getPhone(),LogPubService.MOBILE.LOGIN);
        return CPResponse.create();
    }

    public CPResponse changePwd(CPSingleValueRequest<YWJUserChangePwdDto> request) {
        domainService.changePwd(request.getData());
        return CPResponse.create();
    }


    public CPResponse resetPwd(CPSingleValueRequest<String> request) {
        domainService.resetPwd(request.getData());
        return CPResponse.create();
    }



    @Transactional(readOnly = true)
    public CPResponse pagination(CPSingleValueRequest<YWJUserListDto> requestPack) {
        Page<YWJUser> entitys =domainService.pagination(requestPack.getData());
        return CPSingleValueResponse.from(Pages.map(entitys, YWJUserDto.class));
    }

    private static class SMSHolder {
        private String code ;
        private long time;

        public SMSHolder(String code){
            this.code = code;
            this.time = System.currentTimeMillis();
        }

        public String getCode() {
            return code;
        }


        public long getTime() {
            return time;
        }


    }

}
