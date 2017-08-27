package com.zds.scf.biz.common.dto;

import com.zds.scf.biz.common.dto.base.BaseDto;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserInfoDto extends BaseDto {
    private Long id;
    private String code;
    private String name;
    Set<String> menuResKey = new HashSet();

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getMenuResKey() {
        return menuResKey;
    }

    public void setMenuResKey(Set<String> menuResKey) {
        this.menuResKey = menuResKey;
    }
}

