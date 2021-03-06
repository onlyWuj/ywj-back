package com.zds.scf.biz.ywj.app.dto.device;
import com.zds.scf.biz.common.dto.base.BaseDto;

import javax.validation.constraints.NotNull;


public class YWJDeviceChangeBindDto extends BaseDto {
    @NotNull
    private Long id ;

    private String device;

    private String deviceName;

    private String devicePassWord;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
