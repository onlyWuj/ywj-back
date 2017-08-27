package com.zds.scf.web.common.controller.right;


import com.zds.scf.biz.common.dto.base.CPSingleValueRequest;
import com.zds.scf.biz.common.dto.base.CPViewResultInfo;
import com.zds.scf.biz.common.right.app.service.ResourceAppService;
import com.zds.scf.web.common.controller.AbstractCommonController;
import com.zds.scf.web.common.stereotype.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
public class ResourceController extends AbstractCommonController {
    @Autowired
    private ResourceAppService service;

    @GetMapping("/resource/list.json")
    public CPViewResultInfo list() {
        CPSingleValueRequest requestPack = CPSingleValueRequest.from("");
        return service.list(requestPack).convertTo();
    }

}
