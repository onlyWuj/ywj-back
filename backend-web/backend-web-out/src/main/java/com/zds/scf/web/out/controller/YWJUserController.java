package com.zds.scf.web.out.controller;

import com.zds.scf.biz.common.CPBusinessException;
import com.zds.scf.biz.common.CPContext;
import com.zds.scf.biz.common.dto.base.CPResponse;
import com.zds.scf.biz.common.dto.base.CPSingleValueRequest;
import com.zds.scf.biz.common.dto.base.CPSingleValueResponse;
import com.zds.scf.biz.common.dto.base.CPViewResultInfo;
import com.zds.scf.biz.ywj.app.dto.user.*;
import com.zds.scf.biz.ywj.app.service.YWJUserAppService;
import com.zds.scf.web.common.stereotype.GetMapping;
import com.zds.scf.web.common.stereotype.PostMapping;
import com.zds.scf.web.out.AbstractOutController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017/5/30.
 */
    @RestController(value = "out_YWJUserController")
    public class YWJUserController extends AbstractOutController {

    @Autowired
    private YWJUserAppService service;

    /*@GetMapping("/ywj/user/phone/verify.json")
    public CPViewResultInfo phoneVerify(String phone) {
        CPSingleValueRequest<String> requestPack = CPSingleValueRequest.from(phone);
        CPSingleValueResponse<YWJUserVerifyDto> responsePack = service.verify(requestPack);
        return responsePack.convertTo();
    }*/

    @PostMapping("/ywj/user/register.json")
    public CPViewResultInfo register( YWJUserCreateDto dto) {
        CPSingleValueRequest<YWJUserCreateDto> requestPack = CPSingleValueRequest.from(dto);
        CPResponse response = service.register(requestPack);
        return response.convertTo();
    }

    @PostMapping("/ywj/user/login.json")
    public CPViewResultInfo login( YWJUserLoginDto dto) {
        CPSingleValueRequest<YWJUserLoginDto> requestPack = CPSingleValueRequest.from(dto);
        CPResponse response = service.login(requestPack);
        return response.convertTo();
    }

    @PostMapping("/ywj/user/smsLogin.json")
    public CPViewResultInfo smsLogin( YWJUserSMSLoginDto dto) {
        CPSingleValueRequest<YWJUserSMSLoginDto> requestPack = CPSingleValueRequest.from(dto);
        CPResponse response = service.smsLogin(requestPack);
        return response.convertTo();
    }

    @PostMapping("/ywj/user/changPwd.json")
    public CPViewResultInfo changPwd( YWJUserChangePwdDto dto) {
        CPSingleValueRequest<YWJUserChangePwdDto> requestPack = CPSingleValueRequest.from(dto);
        CPResponse response = service.changePwd(requestPack);
        return response.convertTo();
    }

    @PostMapping("/ywj/user/verifyCodeChangePwd.json")
    public CPViewResultInfo verifyCodeChangePwd( YWJUserVerifyCodeChangePwdDto dto) {
        CPSingleValueRequest<YWJUserVerifyCodeChangePwdDto> requestPack = CPSingleValueRequest.from(dto);
        CPResponse response = service.verifyCodeChangePwd(requestPack);
        return response.convertTo();
    }


}
