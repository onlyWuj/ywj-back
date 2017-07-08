package com.zds.scf.biz.common.udc.app.service;

import com.zds.scf.biz.common.dto.CPSingleValueRequest;
import com.zds.scf.biz.common.dto.CPSingleValueResponse;
import com.zds.scf.biz.common.udc.app.dto.UDCItemDto;
import com.zds.scf.biz.common.udc.domain.entity.UDCItem;
import com.zds.scf.biz.common.udc.domain.service.UDCItemDomainService;
import com.zds.scf.biz.common.util.Pages;
import com.cp.boot.appservice.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
@AppService
@Transactional
public class UDCItemAppService {

    @Autowired
    private UDCItemDomainService domainService;

    @Transactional(readOnly = true)
    public CPSingleValueResponse<UDCItemDto> findById(CPSingleValueRequest<Long> request) {
        return CPSingleValueResponse.from(domainService.findById(request.getData()).to(UDCItemDto.class));
    }

    @Transactional(readOnly = true)
    public CPSingleValueResponse<Page<UDCItemDto>> findAll(CPSingleValueRequest<UDCItemDto> request) {
        Page<UDCItem> UDCItem = domainService.findAll(request.getData());
        return CPSingleValueResponse.from(Pages.map(UDCItem, UDCItemDto.class));
    }
}
