package com.zds.scf.web.common.controller.right;

import com.zds.scf.biz.common.CPContext;
import com.zds.scf.biz.common.dto.CPSingleValueRequest;
import com.zds.scf.biz.common.dto.CPSingleValueResponse;
import com.zds.scf.biz.common.dto.CPViewResultInfo;
import com.zds.scf.biz.common.right.app.dto.user.ChangeUserPwdDto;
import com.zds.scf.biz.common.right.app.dto.user.UserDto;
import com.zds.scf.biz.common.right.app.dto.user.UserListDto;
import com.zds.scf.biz.common.right.app.service.UserAppService;
import com.zds.scf.web.common.controller.AbstractCommonController;
import com.zds.scf.web.common.controller.AbstractController;
import com.zds.scf.web.common.stereotype.GetMapping;
import com.zds.scf.web.common.stereotype.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;


@RestController
public class UserController extends AbstractCommonController {
    @Autowired
    private UserAppService service ;
    @PostMapping("/user/create.json")
    public CPViewResultInfo create(  UserDto dto) {
        CPSingleValueRequest<UserDto> requestPack = CPSingleValueRequest.from(dto);
        CPSingleValueResponse<Long> responsePack = service.create(requestPack);
        return responsePack.convertTo();
    }

    @GetMapping("/user/load.json")
    public CPViewResultInfo load( Long id) {
        CPSingleValueRequest<Long> requestPack = CPSingleValueRequest.from(id);
        CPSingleValueResponse<UserDto> responsePack = service.load(requestPack);
        return responsePack.convertTo();
    }

    @PostMapping("/user/update.json")
    public CPViewResultInfo update(  UserDto dto) {
        CPSingleValueRequest<UserDto> requestPack = CPSingleValueRequest.from(dto);
        CPSingleValueResponse<Long> responsePack = service.update(requestPack);
        return responsePack.convertTo();
    }


    @PostMapping("/user/enable.json")
    public CPViewResultInfo enable( Long id) {
        CPSingleValueRequest<Long> requestPack = CPSingleValueRequest.from(id);
        return service.enable(requestPack).convertTo();
    }

    @PostMapping("/user/disable.json")
    public CPViewResultInfo disable( Long id) {
        CPSingleValueRequest<Long> requestPack = CPSingleValueRequest.from(id);
        return service.disable(requestPack).convertTo();
    }


    @PostMapping("/user/resetPwd.json")
    public CPViewResultInfo resetPwd( Long id) {
        CPSingleValueRequest<Long> requestPack = CPSingleValueRequest.from(id);
        return service.resetPwd(requestPack).convertTo();
    }

    @PostMapping("/user/changePwd.json")
    public CPViewResultInfo changePwd( ChangeUserPwdDto pwdDto) {
        if(Objects.isNull(pwdDto.getId())){
            pwdDto.setId(CPContext.getContext().getUserInfo().getId());
        }
        CPSingleValueRequest<ChangeUserPwdDto> requestPack = CPSingleValueRequest.from(pwdDto);
        return service.changePwd(requestPack).convertTo();
    }


    @GetMapping("/user/pagination.json")
    public CPViewResultInfo pagination(UserListDto dto) {
         CPSingleValueRequest<UserListDto> requestPack = CPSingleValueRequest.from(dto);
        return service.pagination(requestPack).convertTo();

    }
}
