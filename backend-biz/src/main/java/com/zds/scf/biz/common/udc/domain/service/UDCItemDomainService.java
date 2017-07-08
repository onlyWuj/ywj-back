

package com.zds.scf.biz.common.udc.domain.service;

import com.cp.boot.appservice.stereotype.DomainService;
import com.zds.scf.biz.common.udc.app.dto.UDCItemDto;
import com.zds.scf.biz.common.udc.domain.entity.UDCItem;
import com.zds.scf.biz.common.udc.domain.repository.UDCItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

/**
 *
 */
@DomainService
public class UDCItemDomainService {

    @Autowired
    private UDCItemRepository udcItemRepository;

    public UDCItem findById(Long id) {
        return udcItemRepository.findOne(id);
    }

    public Page<UDCItem> findAll(UDCItemDto dto) {
        return udcItemRepository.findAll(dto.toPage());
    }

}
