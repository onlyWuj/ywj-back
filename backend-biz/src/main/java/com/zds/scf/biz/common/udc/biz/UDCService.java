
package com.zds.scf.biz.common.udc.biz;

import com.zds.scf.biz.common.udc.domain.entity.UDCType;

/**
 *
 */
public interface UDCService {
    UDCType findByCode(String code);
}
