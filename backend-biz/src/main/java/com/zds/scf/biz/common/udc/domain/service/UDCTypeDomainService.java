

package com.zds.scf.biz.common.udc.domain.service;

import com.cp.boot.appservice.stereotype.DomainService;
import com.zds.scf.biz.common.udc.domain.entity.UDCItem;
import com.zds.scf.biz.common.udc.domain.repository.UDCItemRepository;
import com.zds.scf.biz.common.udc.domain.repository.UDCTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 *
 */
@DomainService
public class UDCTypeDomainService {

    @Autowired
    private UDCTypeRepository udcTypeRepository;

    @Autowired
    private UDCItemRepository udcItemRepository;


/*    public UDCType findById(Long id) {
        return udcTypeRepository.findOne(id);
    }

    public Page<UDCType> findAll(UDCTypeDto dto) {
        return udcTypeRepository.findAll(dto.toPage());
    }*/

    public List<UDCItem> getByTypeId(Long typeId) {
        return udcItemRepository.findByTypeIdOrderByValue(typeId);

    }

    public List<UDCItem> findByTypeCode(String code) {
        return udcTypeRepository.findByCode(code).getItems();
    }


}
