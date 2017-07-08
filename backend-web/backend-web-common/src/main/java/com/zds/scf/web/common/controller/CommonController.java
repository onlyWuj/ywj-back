/*
 *
 * Copyright (c) 2016 All Rights Reserved
 */

/*
 * 修订记录:
 * 2016-10-26 18:36 创建
 */
package com.zds.scf.web.common.controller;

import com.zds.common.lang.util.PasswdUtil;
import com.zds.common.log.LoggerFactory;
import com.zds.scf.biz.common.CPBusinessException;
import com.zds.scf.biz.common.CPContext;
import com.zds.scf.biz.common.component.SendSmsComponent;
import com.zds.scf.biz.common.dto.CPViewResultInfo;
import com.zds.scf.biz.common.dto.SmsResponseDto;
import com.zds.scf.biz.pbac.domain.entity.SeUser;
import com.zds.scf.biz.pbac.service.PasswordHelper;
import com.zds.scf.biz.pbac.service.SeUserService;
import com.zds.scf.web.common.component.DsTokenComponent;
import com.zds.scf.web.common.component.LoginComponent;
import com.zds.scf.web.common.model.LoginModel;
import com.zds.scf.web.common.model.ModifyPasswordModel;
import com.zds.scf.web.common.model.SmsValidation;
import com.zds.scf.web.common.model.SmsVerifyCodeModel;
import com.zds.scf.web.common.stereotype.GetMapping;
import com.zds.scf.web.common.stereotype.PostMapping;
import com.zds.scf.web.common.util.VerifyCodeUtil;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 *
 */
@RestController
public class CommonController extends AbstractCommonController {
    private static final Logger logger = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private LoginComponent loginComponent;

    @GetMapping("getVerifyCode")
    public void getVerifyCode(HttpServletRequest request,
                              HttpServletResponse response) throws Exception {

        byte[] imageBytes = VerifyCodeUtil.generateImage(request.getSession());
        ServletOutputStream out = response.getOutputStream();
        out.write(imageBytes);
        out.flush();
        out.close();
    }

    @PostMapping("passwordLogin.json")
    @ResponseBody
    public CPViewResultInfo passwordLogin(HttpServletRequest httpServletRequest,  LoginModel loginModel) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        try {
             loginComponent.loginWithPassword(httpServletRequest,loginModel);
        } catch (Exception e) {
            cpViewResultInfo.setSuccess(false);
            cpViewResultInfo.setMessage(e.getMessage());
            return cpViewResultInfo;
        }
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("登录成功");
        return cpViewResultInfo;
    }

    @GetMapping("logout.json")
    @ResponseBody
    public CPViewResultInfo logout() {
        CPViewResultInfo resultInfo = new CPViewResultInfo();
        SecurityUtils.getSubject().logout();
        resultInfo.setSuccess(true);
        return resultInfo;
    }

    @GetMapping("getUserInfo.json")
    @ResponseBody
    public CPViewResultInfo getUserInfo() {
        CPViewResultInfo resultInfo = new CPViewResultInfo();
        CPContext.UserInfo userInfo = CPContext.getContext().getUserInfo();
        if(userInfo==null){
            resultInfo.setSuccess(false);
        }else{
            resultInfo.setSuccess(true);
            resultInfo.setData(userInfo);
        }
        return resultInfo;
    }
}
