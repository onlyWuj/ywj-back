package com.zds.scf.biz.common.right.app.dto.user;
import com.alibaba.fastjson.annotation.JSONField;
import com.zds.scf.biz.common.dto.base.BaseDto;
import com.zds.scf.biz.common.right.app.dto.role.RoleDto;

import javax.validation.constraints.NotNull;
import java.util.List;


public class UserDto extends BaseDto {

    @NotNull(groups = Update.class)
    private Long id;

    private String code;

    private String name;
    @JSONField(serialize = false)
    private String passWord;

    private Boolean available ;

    private String roleID;

    private List<RoleDto> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }

    public List<RoleDto> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDto> roles) {
        this.roles = roles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public interface Update {
    }
}
