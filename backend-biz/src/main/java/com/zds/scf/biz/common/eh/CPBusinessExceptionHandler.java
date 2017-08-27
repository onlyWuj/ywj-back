
package com.zds.scf.biz.common.eh;

import com.cp.boot.appservice.ex.ExceptionContext;
import com.cp.boot.appservice.ex.ExceptionHandler;
import com.zds.common.lang.enums.CommonErrorCode;
import com.zds.common.lang.result.StandardResultInfo;
import com.zds.common.lang.result.Status;
import com.zds.scf.biz.common.CPBusinessException;

/**
 *
 */
public class CPBusinessExceptionHandler implements ExceptionHandler<CPBusinessException> {
    @Override
    public void handle(ExceptionContext<?> context, CPBusinessException ex) {
        StandardResultInfo res = context.getResponse();
        res.setDescription(ex.getMessage());
        res.setStatus(Status.FAIL);
        res.setCode(CommonErrorCode.INVALID_ARGUMENTS.code());
    }
}
