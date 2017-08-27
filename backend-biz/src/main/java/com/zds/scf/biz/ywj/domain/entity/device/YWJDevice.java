package com.zds.scf.biz.ywj.domain.entity.device;

import com.google.common.base.Strings;
import com.zds.common.lang.beans.Copier;
import com.zds.common.lang.exception.Exceptions;
import com.zds.scf.biz.common.domain.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.lang.reflect.Field;


@Entity
@Table(name = "biz_ywj_device", indexes = {@Index(unique = true, name = "biz_ywj_bind_unique_idx",
        columnList = "id")})
public class YWJDevice extends BaseEntity {
    static final private String devcieKey = "ywj_device_passWord_key_YouCanGuess";

    @Column(name = "device", columnDefinition = "varchar(50) NOT NULL comment '设备ID'")
    private String device;

    @Column(name = "phone", columnDefinition = "varchar(30) NOT NULL comment '手机号'")
    private String phone;

    @Column(name = "deviceName", columnDefinition = "varchar(70) NOT NULL comment '设备名'")
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

    @Override
    public void from(Object dto) {
        this.from(dto,"devicePassWord");
        try {
            Field passWordField = dto.getClass().getDeclaredField("devicePassWord");
            passWordField.setAccessible(true);
            String passWord = (String)passWordField.get(dto) ;
            if(!Strings.isNullOrEmpty(passWord)){
                this.setDevicePassWord(AESPlus.encrypt(this.devcieKey,passWord));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T> T to(Class<T> clazz,String... ignorePropeties) {
        try {
            T t = clazz.newInstance();
            Copier.copy(this, t, Copier.CopyStrategy.IGNORE_NULL, Copier.NoMatchingRule.IGNORE,"devicePassWord");
            Field passWordField = clazz.getDeclaredField("devicePassWord");
            passWordField.setAccessible(true);
            String passWord = (this.getDevicePassWord()) ;
            if(!Strings.isNullOrEmpty(passWord)){
                passWordField.set(t,AESPlus.decrypt(this.devcieKey,passWord));
            }
            return t;
        } catch (Exception e) {
            throw Exceptions.newRuntimeException(e);
        }
    }
}
