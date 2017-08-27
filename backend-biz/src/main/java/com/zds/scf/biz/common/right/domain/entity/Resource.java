package com.zds.scf.biz.common.right.domain.entity;

import com.zds.scf.biz.common.domain.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(name = "sys_resource", indexes = {@Index(unique = true, name = "sys_resource_unique_idx",
        columnList = "id")})
public class Resource extends BaseEntity {

    @Column(name = "name", columnDefinition = "varchar(20) DEFAULT NULL comment '资源名称'")
    private String name;

    @Column(name = "url", columnDefinition = "varchar(50) DEFAULT NULL comment '资源路径'")
    private String url;

    @Column(name = "permission", columnDefinition =  "varchar(50) DEFAULT NULL comment '权限字符串'")
    private String permission;

    @Column(name = "parentId", columnDefinition =  "int(11) DEFAULT NULL comment '父ID'")
    private Long parentId;

    @Column(name = "parentIds", columnDefinition =  "varchar(50) DEFAULT NULL comment '父编号列表'")
    private String parentIds;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

}
