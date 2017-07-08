package com.zds.scf.biz.ywj.domain.service;

import com.cp.boot.appservice.stereotype.DomainService;
import com.zds.scf.biz.common.CPBusinessException;
import com.zds.scf.biz.ywj.app.dto.user.YWJUserChangePwdDto;
import com.zds.scf.biz.ywj.app.dto.user.YWJUserCreateDto;
import com.zds.scf.biz.ywj.app.dto.user.YWJUserListDto;
import com.zds.scf.biz.ywj.app.dto.user.YWJUserLoginDto;
import com.zds.scf.biz.ywj.domain.entity.YWJUser;
import com.zds.scf.biz.ywj.domain.repository.YWJUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;

@DomainService
public class YWJUserDomainService {

    @Autowired
    private YWJUserRepository repository;

    @Value("${home.user.initPwd}")
    String initPwd;

    public YWJUser create(YWJUserCreateDto request) {
        YWJUser userInDB = repository.findByPhone(request.getPhone());
        if(!Objects.isNull(userInDB)){
            CPBusinessException.throwIt("用户账号已存在!");
        }
        if(!request.getPassWord().equals(request.getComfirmPassWord())){
            CPBusinessException.throwIt("两次输入密码不一致!");
        }
        YWJUser user = new YWJUser();
        user.from(request);
        user = repository.save(user);
        return user;
    }


    @CachePut(value ="ywj_user",key = "#dto.phone")
    public void changePwd(YWJUserChangePwdDto dto) {
        //1. 加载领域对象
        YWJUser user = repository.findByPhone(dto.getPhone());
        if (Objects.isNull(user)) {
            CPBusinessException.throwIt("用户不存在!");
        }
        if (!dto.getPassWord().equals(user.getPassWord())) {
            CPBusinessException.throwIt("原密码输入错误!");
        }
        if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
            CPBusinessException.throwIt("两次输入密码不一致!");
        }
        user.setPassWord(dto.getNewPassword());
    }

    @CachePut(value ="ywj_user",key = "#phone")
    public void resetPwd(String phone) {
        YWJUser user = repository.findByPhone(phone);
        if (Objects.isNull(user)) {
            CPBusinessException.throwIt("用户不存在!");
        };
        user.setPassWord(initPwd);
    }

    @CachePut(value ="ywj_user",key = "#phone")
    public YWJUser loadByPhone(String phone) {
        return repository.findByPhone(phone);
    }

    public void login(YWJUserLoginDto dto) {
        YWJUser userInDB = repository.findByPhone(dto.getPhone());
        if(Objects.isNull(userInDB)){
            CPBusinessException.throwIt("用户账号不存在!");
        }
        if(!dto.getPassWord().equals(userInDB.getPassWord())){
            CPBusinessException.throwIt("密码不正确!");
        }
    }

    public Page<YWJUser> pagination(YWJUserListDto listParamDto) {
        return repository.findAll((Specification<YWJUser>) listParamDto.createSpecification(), listParamDto.toPage());
    }


}