package com.zds.scf.biz.common.right.app.service;

import com.cp.boot.appservice.AppService;
import com.zds.scf.biz.common.CPBusinessException;
import com.zds.scf.biz.common.CPContext;
import com.zds.scf.biz.common.domain.entity.BaseEntity;
import com.zds.scf.biz.common.dto.CPResponse;
import com.zds.scf.biz.common.dto.CPSingleValueRequest;
import com.zds.scf.biz.common.dto.CPSingleValueResponse;
import com.zds.scf.biz.common.right.app.dto.resource.ResourceDto;
import com.zds.scf.biz.common.right.app.dto.user.ChangeUserPwdDto;
import com.zds.scf.biz.common.right.app.dto.user.UserDto;
import com.zds.scf.biz.common.right.app.dto.user.UserListDto;
import com.zds.scf.biz.common.right.domain.entity.Resource;
import com.zds.scf.biz.common.right.domain.entity.User;
import com.zds.scf.biz.common.right.domain.service.ResourceDomainService;
import com.zds.scf.biz.common.right.domain.service.UserDomainService;
import com.zds.scf.biz.common.util.Pages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@AppService
@Transactional
public class ResourceAppService {

    @Autowired
    private ResourceDomainService domainService;

    @Transactional(readOnly = true)
    public CPSingleValueResponse<List<ResourceDto>> list(CPSingleValueRequest request) {
        List<Resource> entitys =domainService.list();
        return CPSingleValueResponse.from(BaseEntity.map(entitys, ResourceDto.class));
    }
}
