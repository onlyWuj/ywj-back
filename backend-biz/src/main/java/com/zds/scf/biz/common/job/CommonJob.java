package com.zds.scf.biz.common.job;

import com.cp.boot.job.BaseJob;
import com.zds.common.id.CodeGenerator;
import com.zds.scf.biz.common.CPContext;
import com.zds.scf.biz.common.util.UtilPub;

/**
 * Created by yy at 2017/2/12 17:17
 */
public  abstract class CommonJob extends BaseJob {
    public void init() {
        String gid = CodeGenerator.newGID();
        CPContext.getContext().setUserInfo(UtilPub.getSysAdminUserInfo());
    }
     //设置商户ID
    public  Long getMerchantId(){
      return 1l;
    }
}
