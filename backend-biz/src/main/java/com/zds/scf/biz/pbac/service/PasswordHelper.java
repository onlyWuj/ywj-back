package com.zds.scf.biz.pbac.service;

import com.zds.scf.biz.common.right.domain.entity.User;
import com.zds.scf.biz.pbac.domain.entity.SeUser;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Service;

import static com.zds.scf.biz.pbac.PBACConfigration.ALGORITHM_NAME;
import static com.zds.scf.biz.pbac.PBACConfigration.HASH_ITERATIONS;


@Service
public class PasswordHelper {


    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    public void encryptPassword(SeUser user) {

        user.setSalt(randomNumberGenerator.nextBytes().toHex());

        String newPassword = new SimpleHash(
                ALGORITHM_NAME,
                user.getPassword(),
                ByteSource.Util.bytes(user.getCredentialsSalt()),
                HASH_ITERATIONS).toHex();

        user.setPassword(newPassword);
    }
    public  String encryptOldPassword(SeUser user,String oldPassword){
       return  new SimpleHash(
                ALGORITHM_NAME,
                oldPassword,
                ByteSource.Util.bytes(user.getCredentialsSalt()),
                HASH_ITERATIONS).toHex();
    }

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
