package com.zds.scf.biz.common.right.domain.entity;

import com.google.common.base.Strings;
import com.zds.scf.biz.common.domain.entity.BaseEntity;
import com.zds.scf.biz.common.right.domain.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "sys_role", indexes = {@Index(unique = true, name = "sys_role_unique_idx",
        columnList = "id")})
public class Role extends BaseEntity {
    @Autowired
    private transient ResourceRepository resourceRepository;

    @Column(name = "name", columnDefinition = "varchar(50) NOT NULL comment '角色名'")
    private String name;

    @Column(name = "code", columnDefinition = "varchar(50) DEFAULT NULL comment '角色编码'",unique = true)
    private String code;

    @Column(name = "available", columnDefinition = "boolean DEFAULT NULL comment '是否可用'")
    private Boolean available = true;

    @Column(name = "resources", columnDefinition = "varchar(80) comment '拥有资源'")
    private String resourceID;

    private transient List<Resource> resources;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getResourceID() {
        return resourceID;
    }

    public void setResourceID(String resourceID) {
        this.resourceID = resourceID;
    }

    public List<Resource> getResources() {
        if(resources == null ){
            if(Strings.isNullOrEmpty(resourceID)){
                return Collections.EMPTY_LIST;
            }
            String[] ridStr = resourceID.split(",");
            List<Long> rids = new ArrayList(ridStr.length);
            for (int i =0 ;i<ridStr.length;i++){
                try {
                    rids.add(Long.parseLong(ridStr[i]));
                }catch (NumberFormatException e){}
            }
            resources = resourceRepository.findByIdIn(rids.toArray(new Long[rids.size()] ));
        }
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }
}
