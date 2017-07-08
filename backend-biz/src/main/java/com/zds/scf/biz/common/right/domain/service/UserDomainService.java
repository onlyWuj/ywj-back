package com.zds.scf.biz.common.right.domain.service;

import com.cp.boot.appservice.stereotype.DomainService;
import com.google.common.collect.Lists;
import com.zds.scf.biz.common.CPBusinessException;
import com.zds.scf.biz.common.CPContext;
import com.zds.scf.biz.common.dto.SimpleDetailDto;
import com.zds.scf.biz.common.right.app.dto.user.ChangeUserPwdDto;
import com.zds.scf.biz.common.right.app.dto.user.UserDto;
import com.zds.scf.biz.common.right.app.dto.user.UserListDto;
import com.zds.scf.biz.common.right.domain.entity.User;
import com.zds.scf.biz.common.right.domain.repository.UserRepository;
import com.zds.scf.biz.common.udc.UDC;

import com.zds.scf.biz.pbac.service.PasswordHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@DomainService
public class UserDomainService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordHelper passwordHelper;

    @Value("${home.user.initPwd}")
    String initPwd;

    public User create(UserDto request) {
        User userInDB = repository.findByCode(request.getCode());
        if(!Objects.isNull(userInDB)){
            CPBusinessException.throwIt("用户账号已存在");
        }
        User user = new User();
        user.from(request);
        user.setPassWord(initPwd);
        passwordHelper.encryptPassword(user);
        user = repository.save(user);
        return user;
    }

    @CachePut(value ="user",key = "#request.id")
    public User update(UserDto request) {
        //1. 加载领域对象
        User user = repository.findOne(request.getId());
        if (user == null) {
            CPBusinessException.throwIt("id", "不存在");
        }
        User userInDB = repository.findByCode(request.getCode());
        if(!Objects.isNull(userInDB)&&!userInDB.getId().equals(user.getId())){
            CPBusinessException.throwIt("用户账号已存在");
        }
        user.from(request);
        return user;
    }

    @CachePut(value ="user",key = "#id")
    public void disable(Long id) {
        //1. 加载领域对象
        User user = repository.findOne(id);
        if (user == null) {
            CPBusinessException.throwIt("id", "不存在");
        }
        user.setAvailable(false);
    }
    @CachePut(value ="user",key = "#id")
    public void enable(Long id) {
        //1. 加载领域对象
        User user = repository.findOne(id);
        if (user == null) {
            CPBusinessException.throwIt("id", "不存在");
        }
        user.setAvailable(true);

    }

    @CachePut(value ="user",key = "#id")
    public User resetPwd(Long id) {
        //1. 加载领域对象
        User user = repository.findOne(id);
        if (user == null) {
            CPBusinessException.throwIt("id", "不存在");
        }
        user.setPassWord(initPwd);//重置密码为123456
        passwordHelper.encryptPassword(user);
        return user;
    }

    @CachePut(value ="user",key = "#request.id")
    public User changePwd(ChangeUserPwdDto request) {
        //1. 加载领域对象
        User user = repository.findOne(request.getId());
        if (Objects.isNull(user)) {
            CPBusinessException.throwIt("id","用户不存在!");
        }
        String encryptedOldPassword = passwordHelper.encryptOldPassword(user,request.getPassword());
        if (!encryptedOldPassword.equals(user.getPassWord())) {
            CPBusinessException.throwIt("oldPwd", "原密码输入错误!");
        }
        user.setPassWord(request.getNewPassword());
        passwordHelper.encryptPassword(user);
        return user;
    }

    @Cacheable(value = "user",key = "#id")
    public User load(Long id) {
        return repository.findOne(id);
    }

    public User loadByCode(String code) {
        User homeUser = repository.findByCode(code);
        if (homeUser == null) {
            CPBusinessException.throwIt("code", "用户code:"+code+" 不存在");
        }
        return homeUser;
    }

    public Page<User> pagination(UserListDto listParamDto) {
        return repository.findAll((Specification<User>) listParamDto.createSpecification(), listParamDto.toPage());
    }


    public void lock(String userCode) {
     /*   redisTemplate.delete(buildkey(userName, merchantId));
        seUserDao.lock(userName, merchantId);*/
    }
}