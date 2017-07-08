package com.zds.scf.biz.common.right.app.dto.role;


import com.zds.scf.biz.common.dto.PageDto;
import com.zds.scf.biz.common.right.app.dto.resource.ResourceDto;
import com.zds.scf.biz.common.right.domain.entity.Resource;

import javax.validation.constraints.NotNull;
import java.util.List;


public class RoleDto extends PageDto {

    @NotNull(groups = RoleDto.Update.class)
    private Long id;

    private String name;

    private String code;

    private Boolean available ;

    private String resourceID;

    private List<ResourceDto> resources;

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

    public String getResourceID() {
        return resourceID;
    }

    public void setResourceID(String resourceID) {
        this.resourceID = resourceID;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ResourceDto> getResources() {
        return resources;
    }

    public void setResources(List<ResourceDto> resources) {
        this.resources = resources;
    }

    public interface Update {
    }
}
