package com.zds.scf.biz.ywj.app.dto.user;
import com.zds.scf.biz.common.dto.BaseDto;
import org.hibernate.validator.constraints.NotEmpty;


public class YWJUserVerifyDto extends BaseDto {
    @NotEmpty
    private String phone;

    private boolean result ;

    private String verifyCode;

    private int effective;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public int getEffective() {
        return effective;
    }

    public void setEffective(int effective) {
        this.effective = effective;
    }
}
