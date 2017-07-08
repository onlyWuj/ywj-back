package com.zds.scf.web.common.controller;

import com.alibaba.fastjson.JSON;
import com.zds.scf.biz.common.dto.CPSingleValueRequest;
import com.zds.scf.biz.common.dto.CPSingleValueResponse;
import com.zds.scf.biz.common.dto.CPViewResultInfo;
import com.zds.scf.biz.common.udc.UDC;
import com.zds.scf.biz.common.udc.app.dto.UDCItemDto;
import com.zds.scf.biz.common.udc.app.service.UDCTypeAppService;
import com.zds.scf.web.common.stereotype.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UDCController extends AbstractCommonController {

    @Autowired
    private UDCTypeAppService service;

    @GetMapping("/udc/list.json")
    public CPViewResultInfo list(@RequestParam("typeCode")String typeCode) {
        CPSingleValueRequest requestPack = CPSingleValueRequest.from(typeCode);
        CPSingleValueResponse<List<UDCItemDto>> responsePack = service.findByTypeCode(requestPack);
        return responsePack.convertTo();
    }
        @GetMapping("/udc/get.json")
    public CPViewResultInfo testGet(@RequestParam("udcTest")UDC udcTest) {

       CPViewResultInfo cpViewResultInfo=new CPViewResultInfo();
       cpViewResultInfo.setSuccess(true);
       cpViewResultInfo.setData(JSON.toJSONString(udcTest));
      /*  CPSingleValueResponse<List<UDCItemDto>> responsePack = service.findByTypeCode(requestPack);*/
        return cpViewResultInfo;
    }
}
