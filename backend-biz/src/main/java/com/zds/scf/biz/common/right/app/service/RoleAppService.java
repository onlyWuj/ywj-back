package com.zds.scf.biz.common.right.app.service;

import com.cp.boot.appservice.AppService;
import com.zds.scf.biz.common.domain.entity.BaseEntity;
import com.zds.scf.biz.common.dto.CPResponse;
import com.zds.scf.biz.common.dto.CPSingleValueRequest;
import com.zds.scf.biz.common.dto.CPSingleValueResponse;
import com.zds.scf.biz.common.report.ReportService;
import com.zds.scf.biz.common.right.app.dto.role.RoleDto;
import com.zds.scf.biz.common.right.app.dto.role.RoleListDto;
import com.zds.scf.biz.common.right.domain.entity.Role;
import com.zds.scf.biz.common.right.domain.service.RoleDomainService;
import com.zds.scf.biz.common.util.Pages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AppService
@Transactional
public class RoleAppService {

    @Autowired
    private RoleDomainService domainService;

    public CPSingleValueResponse<Long> create(CPSingleValueRequest<RoleDto> request) {
        return CPSingleValueResponse.from(domainService.create(request.getData()).getId());
    }


    @AppService.ValidationGroup()
    public CPSingleValueResponse<Long> update(CPSingleValueRequest<RoleDto> request) {
        return CPSingleValueResponse.from(domainService.update(request.getData()).getId());
    }

    public CPResponse disable(CPSingleValueRequest<Long> request) {
        domainService.disable(request.getData());
        return CPResponse.create();
    }
    public CPResponse enable(CPSingleValueRequest<Long> request) {
        domainService.enable(request.getData());
        return CPResponse.create();
    }

    @Transactional(readOnly = true)
    public CPSingleValueResponse<RoleDto> load(CPSingleValueRequest<Long> request) {
        return CPSingleValueResponse.from(domainService.load(request.getData()).to(RoleDto.class));
    }

    @Transactional(readOnly = true)
    public CPSingleValueResponse<Page<RoleDto>> pagination(CPSingleValueRequest<RoleListDto> request) {
        Page page = Pages.map(domainService.pagination(request.getData()), RoleDto.class);
        CPSingleValueResponse response = CPSingleValueResponse.from(page);
        return response;
    }
    @Transactional(readOnly = true)
    public CPSingleValueResponse<List<RoleDto>>  list(CPSingleValueRequest requestPack) {
        List<Role> entitys =domainService.list();
        return CPSingleValueResponse.from(BaseEntity.map(entitys, RoleDto.class,"resources"));
    }
}
