package com.zds.scf.biz.ywj.domain.entity;

import com.zds.scf.biz.common.domain.entity.BaseEntity;
import com.zds.scf.biz.ywj.domain.entity.device.YWJDevice;
import com.zds.scf.biz.ywj.domain.repository.YWJDeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "biz_ywj_user", indexes = {@Index(unique = true, name = "biz_ywj_user_unique_idx",
        columnList = "id")})
public class YWJUser extends BaseEntity {

    @Autowired
    private transient YWJDeviceRepository deviceRepository;

    @Column(name = "phone", columnDefinition = "varchar(30) NOT NULL comment '手机号'",unique = true)
    private String phone;

    @Column(name = "passWord", columnDefinition = "varchar(80) NOT NULL comment '密码'")
    private String passWord;

    private transient List<YWJDevice> devices;

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

    public List<YWJDevice> getDevices() {
        if(devices == null || devices.size() == 0){
            devices = new ArrayList();
           this.devices = deviceRepository.findByPhone(phone);
        }
        return devices;
    }

    public void setDevices(List<YWJDevice> devices) {
        this.devices = devices;
    }
}
