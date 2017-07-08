package com.zds.scf.biz.ywj.app.service;

import com.cp.boot.appservice.AppService;
import com.zds.scf.biz.common.domain.entity.BaseEntity;
import com.zds.scf.biz.common.dto.CPResponse;
import com.zds.scf.biz.common.dto.CPSingleValueRequest;
import com.zds.scf.biz.common.dto.CPSingleValueResponse;
import com.zds.scf.biz.common.log.app.service.LogPubService;
import com.zds.scf.biz.ywj.app.dto.device.YWJDeviceBindDto;
import com.zds.scf.biz.ywj.app.dto.device.YWJDeviceDto;
import com.zds.scf.biz.ywj.app.dto.device.YWJDeviceListDto;
import com.zds.scf.biz.ywj.app.dto.device.YWJDeviceChangeBindDto;
import com.zds.scf.biz.ywj.domain.entity.YWJDevice;
import com.zds.scf.biz.ywj.domain.service.YWJDeviceDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@AppService
@Transactional
public class YWJDeviceAppService {

    @Autowired
    private YWJDeviceDomainService domainService;

    @Autowired
    private LogPubService logService;

    public CPSingleValueResponse<Long> bind(CPSingleValueRequest<YWJDeviceBindDto> request) {
        YWJDevice entity =domainService.create(request.getData());
        logService.log(request.getData().getPhone(),LogPubService.MOBILE.BIND);
        return CPSingleValueResponse.from(entity.getId());
    }

    public CPSingleValueResponse<Long> changeBind(CPSingleValueRequest<YWJDeviceChangeBindDto> request) {
        YWJDevice entity = domainService.update(request.getData());
        logService.log(entity.getPhone(),LogPubService.MOBILE.CHANGE_BIND);
        return CPSingleValueResponse.from(entity.getId());
    }

    public CPResponse unBind(CPSingleValueRequest<Long> request) {
        YWJDevice entity = domainService.load(request.getData());
        domainService.delete(request.getData());
        logService.log(entity.getPhone(),LogPubService.MOBILE.UNBIND);
        return CPResponse.create();
    }

    @Transactional(readOnly = true)
    public CPResponse listByPhone(CPSingleValueRequest<String> request) {
        List<YWJDevice> entitys =domainService.listByPhone(request.getData());
        return CPSingleValueResponse.from(BaseEntity.map(entitys, YWJDeviceDto.class));
    }

}
