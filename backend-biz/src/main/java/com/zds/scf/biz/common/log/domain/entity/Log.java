package com.zds.scf.biz.common.log.domain.entity;

import com.zds.scf.biz.common.domain.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "sys_log", indexes = {@Index(unique = true, name = "sys_log_unique_idx",
        columnList = "id")})
public class Log extends BaseEntity {

    @Column(name = "operation", columnDefinition = "varchar(20) DEFAULT NULL comment '操作'")
    private String operation;

    @Column(name = "time", columnDefinition = "datetime NOT NULL comment '发生时间'")
    private Date time;

    @Column(name = "appType", columnDefinition =  "varchar(20) DEFAULT NULL comment '应用类型，如后台/移动端'")
    private String appType;

    @Column(name = "user", columnDefinition =  "varchar(30) DEFAULT NULL comment '操作用户标识'")
    private String user;

    @Column(name = "description", columnDefinition =  "varchar(200) DEFAULT NULL comment '描述'")
    private String description;

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
