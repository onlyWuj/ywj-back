package com.zds.scf.biz.common.right.domain.entity;

import com.google.common.base.Strings;
import com.zds.scf.biz.common.CPBusinessException;
import com.zds.scf.biz.common.domain.entity.AggEntity;
import com.zds.scf.biz.common.right.domain.repository.ResourceRepository;
import com.zds.scf.biz.common.right.domain.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Entity
@Table(name = "sys_user", indexes = {@Index(unique = true, name = "sys_user_unique_idx",
        columnList = "id")})
public class User extends AggEntity {

    @Autowired
    private transient RoleRepository roleRepository;

    @Column(name = "code", columnDefinition = "varchar(100) NOT NULL comment '用户账号'",unique = true)
    private String code;

    @Column(name = "name", columnDefinition = "varchar(100) NOT NULL comment '用户名'")
    private String name;

    @Column(name = "passWord", columnDefinition = "varchar(80) NOT NULL comment '密码'")
    private String passWord;

    @Column(name = "roles", columnDefinition = "varchar(80) comment '拥有角色'")
    private String roleID;

    @Column(name = "passWordSalt", columnDefinition = "varchar(50) NOT NULL comment '密码盐'")
    private String passWordSalt;

    @Column(name = "available", columnDefinition = "boolean DEFAULT NULL comment '是否可用'")
    private Boolean available = true;

    private transient List<Role> roles;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getPassWordSalt() {
        return passWordSalt;
    }

    public void setPassWordSalt(String passWordSalt) {
        this.passWordSalt = passWordSalt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }

    public List<Role> getRoles() {
        if(roles == null ){
            if(Strings.isNullOrEmpty(roleID)){
                return Collections.EMPTY_LIST;
            }
            roles = new ArrayList();
            String[] ridStr = roleID.split(",");
            List<Long> rids = new ArrayList(ridStr.length);
            for (int i =0 ;i<ridStr.length;i++){
                try {
                    rids.add(Long.parseLong(ridStr[i]));
                }catch (NumberFormatException e){}
            }
            roles = roleRepository.findByIdIn(rids.toArray(new Long[rids.size()] ));
        }
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getCredentialsSalt() {
        return name + passWordSalt;
    }
}
