
package com.zds.scf.biz.common.udc.domain.repository;

import com.zds.scf.biz.common.domain.repository.BaseRepository;
import com.zds.scf.biz.common.udc.domain.entity.UDCItem;

import java.util.List;

/**
 *
 */
public interface UDCItemRepository extends BaseRepository<UDCItem, Long> {


    List<UDCItem> findByTypeIdOrderByValue(Long typeId);



}
