package com.zds.scf.web.out.controller;

import com.zds.scf.biz.common.CPBusinessException;
import com.zds.scf.biz.common.dto.base.CPResponse;
import com.zds.scf.biz.common.dto.base.CPSingleValueRequest;
import com.zds.scf.biz.common.dto.base.CPSingleValueResponse;
import com.zds.scf.biz.common.dto.base.CPViewResultInfo;
import com.zds.scf.biz.ywj.app.dto.device.YWJDeviceBindDto;
import com.zds.scf.biz.ywj.app.dto.device.YWJDeviceChangeBindDto;
import com.zds.scf.biz.ywj.app.service.YWJDeviceAppService;
import com.zds.scf.biz.ywj.app.service.YWJUserAppService;
import com.zds.scf.web.common.stereotype.GetMapping;
import com.zds.scf.web.common.stereotype.PostMapping;
import com.zds.scf.web.out.AbstractOutController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017/5/30.
 */
    @RestController
    public class YWJDeviceController extends AbstractOutController {

    @Autowired
    private YWJDeviceAppService service;
    @Autowired
    private YWJUserAppService service2;

    @PostMapping("/ywj/device/bind.json")
    public CPViewResultInfo bind( YWJDeviceBindDto dto) {
        CPSingleValueRequest<YWJDeviceBindDto> requestPack = CPSingleValueRequest.from(dto);
        CPSingleValueResponse<Long> responsePack = service.bind(requestPack);
        return responsePack.convertTo();
    }

    @PostMapping("/ywj/device/changeBind.json")
    public CPViewResultInfo changeBind(YWJDeviceChangeBindDto dto) {
        CPSingleValueRequest<YWJDeviceChangeBindDto> requestPack = CPSingleValueRequest.from(dto);
        CPResponse response = service.changeBind(requestPack);
        return response.convertTo();
    }

    @PostMapping("/ywj/device/unBind.json")
    public CPViewResultInfo unBind(Long id) {
        CPSingleValueRequest<Long> requestPack = CPSingleValueRequest.from(id);
        CPResponse response = service.unBind(requestPack);
        return response.convertTo();
    }

    @GetMapping("/ywj/device/listByPhone.json")
    public CPViewResultInfo listByPhone(String phone) {
        CPSingleValueRequest<String> requestPack = CPSingleValueRequest.from(phone);
        CPResponse response = service.listByPhone(requestPack);
        return response.convertTo();
    }

}
