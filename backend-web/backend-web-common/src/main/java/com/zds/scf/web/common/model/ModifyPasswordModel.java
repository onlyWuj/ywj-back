package com.zds.scf.web.common.model;

import com.zds.common.service.CRequestCheckException;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by yy at 2016/11/1 23:41
 */
public class ModifyPasswordModel extends RequestModel {
    @NotNull
    private Long merchantId;
    @NotEmpty
    private String password;
    @NotEmpty
    private String newPassword;
    @NotEmpty
    private String confirmPassword;

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @Override
    public void check() {
        super.check();
        CRequestCheckException.throwIfStateFalse(confirmPassword.equals(password), "confirmPassword", "确认密码不匹配");
    }
}
