
package com.zds.scf.biz.common.eh;

import com.cp.boot.appservice.ex.ExceptionContext;
import com.cp.boot.appservice.ex.ExceptionHandler;
import com.zds.common.lang.result.StandardResultInfo;
import com.zds.common.lang.result.Status;
import org.springframework.dao.DataAccessException;

/**
 *
 */
public class DataAccessExceptionHandler implements ExceptionHandler<DataAccessException> {
    @Override
    public void handle(ExceptionContext<?> context, DataAccessException e) {
        StandardResultInfo res = context.getResponse();
        res.setStatus(Status.FAIL);
        res.setDescription("内部错误");
    }
}
