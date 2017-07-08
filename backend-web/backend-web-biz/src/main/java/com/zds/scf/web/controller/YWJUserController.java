package com.zds.scf.web.controller;


import com.zds.scf.biz.common.dto.CPResponse;
import com.zds.scf.biz.common.dto.CPSingleValueRequest;
import com.zds.scf.biz.common.dto.CPViewResultInfo;
import com.zds.scf.biz.ywj.app.dto.user.YWJUserListDto;
import com.zds.scf.biz.ywj.app.service.YWJUserAppService;
import com.zds.scf.web.AbstractBackendController;
import com.zds.scf.web.common.stereotype.GetMapping;
import com.zds.scf.web.common.stereotype.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController(value = "back_YWJUserController")
public class YWJUserController extends AbstractBackendController {
    @Autowired
    private YWJUserAppService service;

    @PostMapping("/ywj/user/resetPwd.json")
    public CPViewResultInfo resetPwd(String phone) {
        CPSingleValueRequest<String> requestPack = CPSingleValueRequest.from(phone);
        CPResponse response = service.resetPwd(requestPack);
        return response.convertTo();
    }

    @GetMapping("/ywj/user/pagination.json")
    public CPViewResultInfo pagination(YWJUserListDto dto) {
        CPSingleValueRequest<YWJUserListDto> requestPack = CPSingleValueRequest.from(dto);
        return service.pagination(requestPack).convertTo();
    }


}
