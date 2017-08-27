package com.zds.scf.biz.ywj.domain.service;

import com.cp.boot.appservice.stereotype.DomainService;
import com.zds.scf.biz.common.CPBusinessException;
import com.zds.scf.biz.pbac.service.PasswordHelper;
import com.zds.scf.biz.ywj.app.dto.device.YWJDeviceBindDto;
import com.zds.scf.biz.ywj.app.dto.device.YWJDeviceChangeBindDto;
import com.zds.scf.biz.ywj.domain.entity.device.YWJDevice;
import com.zds.scf.biz.ywj.domain.repository.YWJDeviceRepository;
import com.zds.scf.biz.ywj.domain.repository.YWJUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Objects;

@DomainService
public class YWJDeviceDomainService {

    @Autowired
    private YWJDeviceRepository repository;

    @Autowired
    private YWJUserRepository userRepository;

    @Autowired
    private PasswordHelper passwordHelper;


    public YWJDevice create(YWJDeviceBindDto dto) {
        YWJDevice deviceInDB = repository.findByDeviceAndPhone(dto.getDevice(),dto.getPhone());
        if(!Objects.isNull(deviceInDB)){
            CPBusinessException.throwIt("设备已绑定!");
        }
        if(Objects.isNull(userRepository.findByPhone(dto.getPhone()))){
            CPBusinessException.throwIt("用户未注册!");
        }

        YWJDevice device = new YWJDevice();
        device.from(dto);
        device = repository.save(device);
        return device;
    }

    @Cacheable(value ="ywj_device",key = "#id")
    public YWJDevice load(Long id) {
        return  repository.findOne(id);
    }

    public List<YWJDevice> loadByDeviceId(String device) {
        return  repository.findByDevice(device);
    }

    @CachePut(value ="ywj_device",key = "#dto.id")
    public YWJDevice update(YWJDeviceChangeBindDto dto) {
        //1. 加载领域对象
        YWJDevice deviceInDB = repository.findOne(dto.getId());
        if (deviceInDB == null) {
            CPBusinessException.throwIt("绑定设备不存在!");
        } else {
            deviceInDB.from(dto);
        }
        return deviceInDB;
    }

    @CacheEvict(value ="ywj_device",key = "#id")
    public void delete(Long id) {
        //1. 加载领域对象
        YWJDevice deviceInDB = repository.findOne(id);
        if (deviceInDB == null) {
            CPBusinessException.throwIt("绑定设备不存在!");
        }
        repository.delete(deviceInDB);
    }

    public List<YWJDevice> listByPhone(String phone) {
        return repository.findByPhone(phone);
    }

}