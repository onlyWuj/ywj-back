
package com.zds.scf.biz.common.dto;

import com.zds.scf.biz.common.CPContext;
import com.zds.common.service.CRequestBase;
import com.zds.common.util.ToString;

/**
 * 请求对象
 *
 *
 */
public class CPRequest extends CRequestBase {

    private Long merchantId;

    private CPContext.UserInfo userInfo;

    public CPRequest() {
        this.setGid(CPContext.getContext().getGid());
        this.setUserInfo(CPContext.getContext().getUserInfo());
    }

    @ToString.Invisible
    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    @ToString.Invisible
    public CPContext.UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(CPContext.UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
