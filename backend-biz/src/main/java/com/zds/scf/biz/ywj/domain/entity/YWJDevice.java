package com.zds.scf.biz.ywj.domain.entity;

import com.zds.scf.biz.common.CPBusinessException;
import com.zds.scf.biz.common.domain.entity.AggEntity;
import com.zds.scf.biz.common.domain.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;


@Entity
@Table(name = "biz_ywj_device", indexes = {@Index(unique = true, name = "biz_ywj_bind_unique_idx",
        columnList = "id")})
public class YWJDevice extends BaseEntity {
    @Column(name = "device", columnDefinition = "varchar(50) NOT NULL comment '设备ID'",unique = true)
    private String device;

    @Column(name = "phone", columnDefinition = "varchar(30) NOT NULL comment '手机号'")
    private String phone;

    @Column(name = "deviceName", columnDefinition = "varchar(50) NOT NULL comment '设备名'")
    private String deviceName;

    @Column(name = "devicePassWord", columnDefinition = "varchar(50) NOT NULL comment '密码'")
    private String devicePassWord;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDevicePassWord() {
        return devicePassWord;
    }

    public void setDevicePassWord(String devicePassWord) {
        this.devicePassWord = devicePassWord;
    }
}
