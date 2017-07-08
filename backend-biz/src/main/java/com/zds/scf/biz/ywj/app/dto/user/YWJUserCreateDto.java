package com.zds.scf.biz.ywj.app.dto.user;
import com.zds.scf.biz.common.dto.BaseDto;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;


public class YWJUserCreateDto extends BaseDto {

    @NotEmpty
    private String phone;

    @NotEmpty
    private String passWord;

    @NotEmpty
    private String comfirmPassWord;

    @NotEmpty
    private String verifyCode;

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

    public String getComfirmPassWord() {
        return comfirmPassWord;
    }

    public void setComfirmPassWord(String comfirmPassWord) {
        this.comfirmPassWord = comfirmPassWord;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}
