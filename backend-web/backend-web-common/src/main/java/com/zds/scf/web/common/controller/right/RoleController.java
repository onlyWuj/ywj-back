package com.zds.scf.web.common.controller.right;

import com.zds.scf.biz.common.dto.base.CPSingleValueRequest;
import com.zds.scf.biz.common.dto.base.CPSingleValueResponse;
import com.zds.scf.biz.common.dto.base.CPViewResultInfo;
import com.zds.scf.biz.common.right.app.dto.role.RoleDto;
import com.zds.scf.biz.common.right.app.dto.role.RoleListDto;
import com.zds.scf.biz.common.right.app.service.RoleAppService;
import com.zds.scf.web.common.controller.AbstractCommonController;
import com.zds.scf.web.common.stereotype.GetMapping;
import com.zds.scf.web.common.stereotype.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RoleController extends AbstractCommonController {
    @Autowired
    private RoleAppService service ;
    @PostMapping("/role/create.json")
    public CPViewResultInfo create(  RoleDto dto) {
        CPSingleValueRequest<RoleDto> requestPack = CPSingleValueRequest.from(dto);
        CPSingleValueResponse<Long> responsePack = service.create(requestPack);
        return responsePack.convertTo();
    }

    @PostMapping("/role/update.json")
    public CPViewResultInfo update(  RoleDto dto) {
        CPSingleValueRequest<RoleDto> requestPack = CPSingleValueRequest.from(dto);
        CPSingleValueResponse<Long> responsePack = service.update(requestPack);
        return responsePack.convertTo();
    }

    @PostMapping("/role/enable.json")
    public CPViewResultInfo enable( Long id) {
        CPSingleValueRequest<Long> requestPack = CPSingleValueRequest.from(id);
        return service.enable(requestPack).convertTo();
    }

    @PostMapping("/role/disable.json")
    public CPViewResultInfo disable( Long id) {
        CPSingleValueRequest<Long> requestPack = CPSingleValueRequest.from(id);
        return service.disable(requestPack).convertTo();
    }

    @GetMapping("/role/load.json")
    public CPViewResultInfo load( Long id) {
        CPSingleValueRequest<Long> requestPack = CPSingleValueRequest.from(id);
        CPSingleValueResponse<RoleDto> responsePack = service.load(requestPack);
        return responsePack.convertTo();
    }

    @GetMapping("/role/pagination.json")
    public CPViewResultInfo pagination(RoleListDto dto) {
         CPSingleValueRequest<RoleListDto> requestPack = CPSingleValueRequest.from(dto);
        return service.pagination(requestPack).convertTo();

    }

    @GetMapping("/role/list.json")
    public CPViewResultInfo list() {
        CPSingleValueRequest requestPack = CPSingleValueRequest.from("");
        return service.list(requestPack).convertTo();

    }
}
