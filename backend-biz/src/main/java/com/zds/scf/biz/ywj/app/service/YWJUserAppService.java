package com.zds.scf.biz.ywj.app.service;

import com.cp.boot.appservice.AppService;
import com.zds.scf.biz.common.CPBusinessException;
import com.zds.scf.biz.common.dto.SMSVerifyDto;
import com.zds.scf.biz.common.dto.base.CPResponse;
import com.zds.scf.biz.common.dto.base.CPSingleValueRequest;
import com.zds.scf.biz.common.dto.base.CPSingleValueResponse;
import com.zds.scf.biz.common.log.app.service.LogPubService;
import com.zds.scf.biz.common.util.Pages;
import com.zds.scf.biz.ywj.app.dto.user.*;
import com.zds.scf.biz.ywj.app.service.sms.VerifyService;
import com.zds.scf.biz.ywj.domain.entity.YWJUser;
import com.zds.scf.biz.ywj.domain.service.YWJUserDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

@AppService
@Transactional
public class YWJUserAppService {



    @Autowired
    private YWJUserDomainService domainService;

    @Autowired
    private LogPubService logService;

    @Autowired
    private VerifyService verifyService;

    public CPSingleValueResponse<Long> register(CPSingleValueRequest<YWJUserCreateDto> request) {
        YWJUserCreateDto dto = request.getData();
        verifyService.verifyCode(dto.getPhone(),dto.getVerifyCode());
        Long id = domainService.create(dto).getId();
        logService.log(dto.getPhone(),LogPubService.MOBILE.REGISTER);
        return CPSingleValueResponse.from(id );
    }

   /* public CPSingleValueResponse<YWJUserVerifyDto> verify(CPSingleValueRequest<String> requestPack) {
        YWJUser user = domainService.loadByPhone(requestPack.getData());
        YWJUserVerifyDto dto = new YWJUserVerifyDto();
        dto.setPhone(requestPack.getData());
        if(user!=null){
            CPBusinessException.throwIt("用户:"+dto.getPhone()+" 已注册!");
        }
        String smsString = sendClient.sendSmsCode(requestPack.getData());
        JSONObject json =  JSONObject.parseObject(smsString);
        if((Integer)json.get("code")!=200){
            CPBusinessException.throwIt(String.valueOf(json.get("msg")));
        }
        String correctVerify =  String.valueOf(json.get("obj"));
        dto.setVerifyCode(correctVerify);
        verifyCodeMap.put(requestPack.getData(),new SMSHolder(correctVerify));
        dto.setEffective(effectiveTime);
        return  CPSingleValueResponse.from(dto);

    }*/


    public CPResponse login(CPSingleValueRequest<YWJUserLoginDto> request) {
        domainService.login(request.getData());
        logService.log(request.getData().getPhone(),LogPubService.MOBILE.LOGIN);
        return CPResponse.create();
    }
    public CPResponse smsLogin(CPSingleValueRequest<YWJUserSMSLoginDto> request) {
        domainService.smsLogin(request.getData());
        logService.log(request.getData().getPhone(),LogPubService.MOBILE.LOGIN);
        return CPResponse.create();
    }

    public CPResponse changePwd(CPSingleValueRequest<YWJUserChangePwdDto> request) {
        domainService.changePwd(request.getData());
        return CPResponse.create();
    }

    public CPResponse verifyCodeChangePwd(CPSingleValueRequest<YWJUserVerifyCodeChangePwdDto> request) {
        domainService.verifyCodeChangePwd(request.getData());
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


}
