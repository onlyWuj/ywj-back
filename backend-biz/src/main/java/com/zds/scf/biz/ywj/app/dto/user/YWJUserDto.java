package com.zds.scf.biz.ywj.app.dto.user;

import com.alibaba.fastjson.annotation.JSONField;
import com.zds.scf.biz.common.dto.base.BaseDto;
import com.zds.scf.biz.ywj.app.dto.device.YWJDeviceDto;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;


public class YWJUserDto extends BaseDto {

    @NotNull
    private Long id;
    @NotEmpty
    private String phone;

    @JSONField(serialize = false)
    private String passWord;

    private  List<YWJDeviceDto> devices;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public List<YWJDeviceDto> getDevices() {
        return devices;
    }

    public void setDevices(List<YWJDeviceDto> devices) {
        this.devices = devices;
    }
}
