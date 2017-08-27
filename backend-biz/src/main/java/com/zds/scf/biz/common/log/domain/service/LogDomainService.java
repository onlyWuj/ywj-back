package com.zds.scf.biz.common.log.domain.service;

import com.zds.scf.biz.common.log.app.dto.LogDto;
import com.zds.scf.biz.common.log.domain.entity.Log;
import com.zds.scf.biz.common.log.domain.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LogDomainService {

    @Autowired
    private LogRepository repository;

    public void log(LogDto dto) {
        Log log = new Log();
        log.from(dto);
        repository.save(log);
    }
}