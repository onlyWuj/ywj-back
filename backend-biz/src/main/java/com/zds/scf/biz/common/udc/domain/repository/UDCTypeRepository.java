
package com.zds.scf.biz.common.udc.domain.repository;

import com.zds.scf.biz.common.domain.repository.BaseRepository;
import com.zds.scf.biz.common.udc.domain.entity.UDCType;

/**
 *
 */
public interface UDCTypeRepository extends BaseRepository<UDCType, Long> {

    UDCType findByCode(String code);

    //    @Query(value = "SELECT * FROM pf_enum where enum_type_id=?1 and enum_value=?2", nativeQuery = true)
    //    UDCItem findeByEnumTypeAndEnumValue(Integer enumTypeID, Integer enumValue);

    //    @Query(value = "SELECT * FROM pf_enum INNER JOIN pf_enum_type ON pf_enum.enum_type_id = pf_enum_type.enum_type_id where pf_enum_type.enum_type_code=?1", nativeQuery = true)
    //    public Set<UDCItem> findByEnumTypeCode(String enumTypeCode);
}
