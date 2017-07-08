package com.zds.scf.biz.common.udc.app.service;

import com.zds.scf.biz.common.domain.entity.BaseEntity;
import com.zds.scf.biz.common.dto.CPSingleValueRequest;
import com.zds.scf.biz.common.dto.CPSingleValueResponse;
import com.zds.scf.biz.common.udc.app.dto.UDCItemDto;
import com.zds.scf.biz.common.udc.domain.entity.UDCItem;
import com.zds.scf.biz.common.udc.domain.service.UDCTypeDomainService;
import com.cp.boot.appservice.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 */
@AppService
@Transactional
public class UDCTypeAppService {

    @Autowired
    private UDCTypeDomainService domainService;

    /**
     * 根据UDC类型取Item
     *
     * @param request typeId
     * @return CPSingleValueResponse<List<UDCItemDto>>
     */
    @Transactional(readOnly = true)
    public CPSingleValueResponse<List<UDCItemDto>> findAll(CPSingleValueRequest<Long> request) {
        List<UDCItem> udcItems = domainService.getByTypeId(request.getData());
        return CPSingleValueResponse.from(BaseEntity.map(udcItems, UDCItemDto.class));
    }

    @Transactional(readOnly = true)
    public CPSingleValueResponse<List<UDCItemDto>> findByTypeCode(CPSingleValueRequest<String> request) {
        List<UDCItem> udcItems = domainService.findByTypeCode(request.getData());
        return CPSingleValueResponse.from(BaseEntity.map(udcItems, UDCItemDto.class));
    }
}
