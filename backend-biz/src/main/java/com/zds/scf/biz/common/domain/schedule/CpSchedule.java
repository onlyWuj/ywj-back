package com.zds.scf.biz.common.domain.schedule;

import com.zds.common.id.CodeGenerator;
import com.zds.scf.biz.common.CPContext;
import com.zds.scf.biz.common.util.UtilPub;


/**
 * 定时任务
 *
 */
public abstract class CpSchedule {

    public void init() {
        String gid = CodeGenerator.newGID();
        CPContext.getContext().setGid(gid);
        CPContext.getContext().setUserInfo(UtilPub.getSysAdminUserInfo());
    }

    public abstract Long getMerchantId();//设置商户ID
}
