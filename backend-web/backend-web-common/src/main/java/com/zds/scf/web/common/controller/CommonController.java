/*
 *
 * Copyright (c) 2016 All Rights Reserved
 */

/*
 * 修订记录:
 * 2016-10-26 18:36 创建
 */
package com.zds.scf.web.common.controller;

import com.zds.common.log.LoggerFactory;
import com.zds.scf.biz.common.CPContext;
import com.zds.scf.biz.common.dto.UserInfoDto;
import com.zds.scf.biz.common.dto.base.CPViewResultInfo;
import com.zds.scf.biz.common.right.app.service.UserAppService;
import com.zds.scf.biz.common.right.domain.entity.Resource;
import com.zds.scf.biz.common.right.domain.entity.Role;
import com.zds.scf.biz.common.right.domain.entity.User;
import com.zds.scf.biz.common.right.domain.service.UserDomainService;
import com.zds.scf.web.common.component.LoginComponent;
import com.zds.scf.web.common.model.LoginModel;
import com.zds.scf.web.common.stereotype.GetMapping;
import com.zds.scf.web.common.stereotype.PostMapping;
import com.zds.scf.web.common.util.VerifyCodeUtil;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 */
@RestController
public class CommonController extends AbstractCommonController {
    private static final Logger logger = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private LoginComponent loginComponent;

    @Autowired
    private UserDomainService userDomainService;

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
            User user = userDomainService.load(userInfo.getId());
            UserInfoDto dto  = user.to(UserInfoDto.class);
            for (Role role : user.getRoles()){
                for (Resource resource : role.getResources()){
                    dto.getMenuResKey().add(resource.getUrl());
                }
            }
            resultInfo.setData(dto);
        }
        return resultInfo;
    }
}
