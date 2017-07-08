
package com.zds.scf.biz.common.eh;

import com.zds.scf.biz.common.CPBusinessException;
import com.cp.boot.appservice.ex.ExceptionContext;
import com.cp.boot.appservice.ex.ExceptionHandler;
import com.zds.common.lang.result.StandardResultInfo;
import com.zds.common.lang.result.Status;
import com.zds.common.service.CRequestCheckException;
import org.springframework.transaction.TransactionException;

/**
 *
 */
public class TransactionExceptionHandler implements ExceptionHandler<TransactionException> {
    @Override
    public void handle(ExceptionContext<?> context, TransactionException e) {
        StandardResultInfo res = context.getResponse();
        res.setStatus(Status.FAIL);
        Throwable throwable = e.getCause();
        while (throwable != null && !(throwable instanceof CPBusinessException || throwable instanceof CRequestCheckException)) {
            throwable = throwable.getCause();
        }
        if (throwable == null) {
            res.setDescription("内部错误");
        } else {
            res.setDescription(throwable.getMessage());
        }
    }
}
