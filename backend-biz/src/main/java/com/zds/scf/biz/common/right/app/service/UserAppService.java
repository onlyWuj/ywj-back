package com.zds.scf.biz.common.right.app.service;

import com.cp.boot.appservice.AppService;
import com.zds.scf.biz.common.CPBusinessException;
import com.zds.scf.biz.common.CPContext;
import com.zds.scf.biz.common.dto.base.CPResponse;
import com.zds.scf.biz.common.dto.base.CPSingleValueRequest;
import com.zds.scf.biz.common.dto.base.CPSingleValueResponse;
import com.zds.scf.biz.common.right.app.dto.user.ChangeUserPwdDto;
import com.zds.scf.biz.common.right.app.dto.user.UserDto;
import com.zds.scf.biz.common.right.app.dto.user.UserListDto;
import com.zds.scf.biz.common.right.domain.entity.User;
import com.zds.scf.biz.common.right.domain.service.UserDomainService;
import com.zds.scf.biz.common.util.Pages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;


@AppService
@Transactional
public class UserAppService {

    @Autowired
    private UserDomainService domainService;

    public CPSingleValueResponse<Long> create(CPSingleValueRequest<UserDto> request) {
        return CPSingleValueResponse.from(domainService.create(request.getData()).getId());
    }

    @AppService.ValidationGroup()
    public CPSingleValueResponse<Long> update(CPSingleValueRequest<UserDto> request) {
        return CPSingleValueResponse.from(domainService.update(request.getData()).getId());
    }

    public CPResponse disable(CPSingleValueRequest<Long> request) {
        domainService.disable(request.getData());
        return CPResponse.create();
    }
    public CPResponse enable(CPSingleValueRequest<Long> request) {
        domainService.enable(request.getData());
        return CPResponse.create();
    }

    public CPResponse resetPwd(CPSingleValueRequest<Long> request) {
        return CPSingleValueResponse.from(domainService.resetPwd(request.getData()).getId());
    }

    public CPResponse changePwd(CPSingleValueRequest<ChangeUserPwdDto> request) {
        ChangeUserPwdDto pwdDto =request.getData();
        if (!pwdDto.getNewPassword().equals(pwdDto.getConfirmPassword())) {
            CPBusinessException.throwIt("新密码两次输入不一致!");
        }
        pwdDto.setId(CPContext.getContext().getUserInfo().getId());
        User user = domainService.changePwd(pwdDto);
        return CPSingleValueResponse.from(user.getId());
    }

    @Transactional(readOnly = true)
    public CPSingleValueResponse<UserDto> load(CPSingleValueRequest<Long> request) {
        User entity =domainService.load(request.getData());
        UserDto dto = entity.to(UserDto.class);
        return CPSingleValueResponse.from(dto);
    }

    @Transactional(readOnly = true)
    public CPSingleValueResponse<Page<UserDto>> pagination(CPSingleValueRequest<UserListDto> request) {
        Page<User> entitys =domainService.pagination(request.getData());
        return CPSingleValueResponse.from(Pages.map(entitys, UserDto.class));
    }
}
