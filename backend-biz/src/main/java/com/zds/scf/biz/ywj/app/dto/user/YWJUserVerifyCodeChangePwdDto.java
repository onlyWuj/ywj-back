package com.zds.scf.biz.ywj.app.dto.user;
import com.zds.scf.biz.common.dto.base.BaseDto;
import org.hibernate.validator.constraints.NotEmpty;


public class YWJUserVerifyCodeChangePwdDto extends BaseDto {
    @NotEmpty
    private String phone;
    @NotEmpty
    private String verifyCode;
    @NotEmpty
    private String newPassword;
    @NotEmpty
    private String confirmPassword;

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

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
