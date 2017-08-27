package com.zds.scf.biz.ywj.app.dto.user;
import com.zds.scf.biz.common.dto.base.BaseDto;
import org.hibernate.validator.constraints.NotEmpty;


public class YWJUserLoginDto extends BaseDto {
    @NotEmpty
    private String phone;
    @NotEmpty
    private String passWord;

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

}
