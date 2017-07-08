
package com.zds.scf.biz.common.eh;

import com.cp.boot.appservice.ex.ExceptionContext;
import com.cp.boot.appservice.ex.ExceptionHandler;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.zds.common.lang.enums.CommonErrorCode;
import com.zds.common.lang.result.StandardResultInfo;
import com.zds.common.lang.result.Status;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;

/**
 *
 */
public class DataIntegrityViolationExceptionHandler implements ExceptionHandler<DataIntegrityViolationException> {
    @Override
    public void handle(ExceptionContext<?> context, DataIntegrityViolationException e) {
        if (e.getCause() instanceof ConstraintViolationException) {
            ConstraintViolationException constraintViolationException = (ConstraintViolationException) e.getCause();
            if (constraintViolationException.getCause() instanceof MySQLIntegrityConstraintViolationException) {
                MySQLIntegrityConstraintViolationException throwables = (MySQLIntegrityConstraintViolationException) constraintViolationException.getCause();
                StandardResultInfo res = context.getResponse();
                String description = throwables.getMessage();
                String errorMessage;
                if (description.contains("credit_limit_unique_idx")) {
                    errorMessage = "供应商不能重复";
                }
                else {
                    errorMessage ="内部错误";
                }
                res.setDescription(errorMessage);
                res.setStatus(Status.FAIL);
                res.setCode(CommonErrorCode.DB_ERROR.code());
            }
        } else {
            StandardResultInfo res = context.getResponse();
            res.setDescription(e.getMessage());
            res.setStatus(Status.FAIL);
            res.setCode(CommonErrorCode.DB_ERROR.code());
        }
    }
}
