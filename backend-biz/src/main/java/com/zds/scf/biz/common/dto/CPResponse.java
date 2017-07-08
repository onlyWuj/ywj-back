
package com.zds.scf.biz.common.dto;

import com.zds.common.lang.result.StandardResultInfo;
import com.zds.common.lang.result.Status;

/**
 * 响应对象
 *
 *
 */
public class CPResponse extends StandardResultInfo {
    public CPResponse() {
        super(Status.SUCCESS);
    }
    /**
     * 把当前对象转换为CPViewResultInfo对象
     */
    public CPViewResultInfo convertTo() {
        return CPViewResultInfo.form(this);
    }

    public static CPResponse create(){
        return new CPResponse();
    }
}
