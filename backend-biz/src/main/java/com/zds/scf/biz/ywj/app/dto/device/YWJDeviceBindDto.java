package com.zds.scf.biz.ywj.app.dto.device;
import com.zds.scf.biz.common.dto.BaseDto;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;


public class YWJDeviceBindDto extends BaseDto {

    @NotEmpty
    private String phone;
    @NotEmpty
    private String device;

    private String deviceName;

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
