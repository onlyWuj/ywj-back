package com.zds.scf.biz.common.log.app.service;

import com.zds.scf.biz.common.log.app.dto.LogDto;
import com.zds.scf.biz.common.log.domain.service.LogDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by Administrator on 2017/6/7.
 */
@Component
public class LogPubService {

    @Autowired
    private LogDomainService logDomainService;

    public void log(String phone, Operation register) {
        LogDto dto = new LogDto();
        dto.setUser(phone);
        dto.setAppType(register.app);
        dto.setOperation(register.operation);
        dto.setTime(new Date());
        logDomainService.log(dto);
    }

    public static abstract class Operation {
        private String app;
        private String operation;

        public Operation(String app, String operation) {
            this.app = app;
            this.operation = operation;
        }

        public String getOperation() {
            return operation;
        }

        public void setOperation(String operation) {
            this.operation = operation;
        }

        public abstract Operation getOperationInfo();
    }
    public static class BACK {
    }

    public static class MOBILE extends Operation{
        private static String app = "MOBILE";
        public static MOBILE REGISTER = new MOBILE("REGISTER");
        public static MOBILE LOGIN = new MOBILE("LOGIN");
        public static MOBILE BIND = new MOBILE("BIND");
        public static MOBILE CHANGE_BIND = new MOBILE("CHANGE_BIND");
        public static MOBILE UNBIND = new MOBILE("UNBIND");

        public MOBILE(String operation) {
            super(app,operation);
        }


        @Override
        public Operation getOperationInfo() {
            return this;
        }


    }
}
