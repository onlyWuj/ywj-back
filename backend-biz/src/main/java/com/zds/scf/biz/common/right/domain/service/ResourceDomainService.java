package com.zds.scf.biz.common.right.domain.service;

import com.cp.boot.appservice.stereotype.DomainService;
import com.zds.scf.biz.common.CPBusinessException;
import com.zds.scf.biz.common.right.app.dto.user.ChangeUserPwdDto;
import com.zds.scf.biz.common.right.app.dto.user.UserDto;
import com.zds.scf.biz.common.right.app.dto.user.UserListDto;
import com.zds.scf.biz.common.right.domain.entity.Resource;
import com.zds.scf.biz.common.right.domain.entity.User;
import com.zds.scf.biz.common.right.domain.repository.ResourceRepository;
import com.zds.scf.biz.common.right.domain.repository.UserRepository;
import com.zds.scf.biz.pbac.service.PasswordHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Objects;

@DomainService
public class ResourceDomainService {

    @Autowired
    private ResourceRepository repository;

    public List<Resource> list() {
        return repository.findAll();
    }
}