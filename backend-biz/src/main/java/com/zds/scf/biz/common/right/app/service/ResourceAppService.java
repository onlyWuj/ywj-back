package com.zds.scf.biz.common.right.app.service;

import com.cp.boot.appservice.AppService;
import com.zds.scf.biz.common.domain.entity.BaseEntity;
import com.zds.scf.biz.common.dto.base.CPSingleValueRequest;
import com.zds.scf.biz.common.dto.base.CPSingleValueResponse;
import com.zds.scf.biz.common.right.app.dto.resource.ResourceDto;
import com.zds.scf.biz.common.right.domain.entity.Resource;
import com.zds.scf.biz.common.right.domain.service.ResourceDomainService;
import org.springframework.beans.factory.annotation.Autowired;
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
