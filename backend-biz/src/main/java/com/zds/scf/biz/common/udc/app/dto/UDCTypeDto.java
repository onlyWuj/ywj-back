package com.zds.scf.biz.common.udc.app.dto;

import com.zds.common.service.CRequestCheckException;
import com.zds.scf.biz.common.dto.pub.PageDto;

import javax.validation.constraints.NotNull;

/**
 *
 */
public class UDCTypeDto extends PageDto {

    @NotNull(groups = Update.class)
    private Long id;

    @NotNull
    private String code;

    @NotNull
    private String name;

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

    /**
     * 执行Update校验组时，默认会调用此方法
     */
    public void checkOnUpdate(CRequestCheckException e) {

    }


    public interface Update {

    }
}
