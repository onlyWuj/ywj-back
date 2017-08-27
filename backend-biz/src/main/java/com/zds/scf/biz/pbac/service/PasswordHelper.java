package com.zds.scf.biz.pbac.service;

import com.zds.scf.biz.common.right.domain.entity.User;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import static com.zds.scf.biz.pbac.PBACConfigration.ALGORITHM_NAME;
import static com.zds.scf.biz.pbac.PBACConfigration.HASH_ITERATIONS;


@Service
public class PasswordHelper {



    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    public void encryptPassword(User user) {

        user.setPassWordSalt(randomNumberGenerator.nextBytes().toHex());

        String newPassword = new SimpleHash(
                ALGORITHM_NAME,
                user.getPassWord(),
                ByteSource.Util.bytes(user.getCredentialsSalt()),
                HASH_ITERATIONS).toHex();

        user.setPassWord(newPassword);
    }
    public  String encryptOldPassword(User user,String oldPassword){
        return  new SimpleHash(
                ALGORITHM_NAME,
                oldPassword,
                ByteSource.Util.bytes(user.getCredentialsSalt()),
                HASH_ITERATIONS).toHex();
    }
}
