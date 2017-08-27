package com.zds.scf.biz.ywj.app.dto.user;
import com.zds.scf.biz.common.dto.base.BaseDto;
import org.hibernate.validator.constraints.NotEmpty;


public class YWJUserSMSLoginDto extends BaseDto {
    @NotEmpty
    private String phone;
    @NotEmpty
    private String verifyCode;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}
