package com.zds.scf.biz.ywj.domain.repository;


import com.zds.scf.biz.common.domain.repository.BaseRepository;
import com.zds.scf.biz.ywj.domain.entity.YWJDevice;
import com.zds.scf.biz.ywj.domain.entity.YWJUser;

import java.util.List;

public interface YWJDeviceRepository extends BaseRepository<YWJDevice, Long> {

    public YWJDevice findByDevice(String deviceId);

    public YWJDevice findByDeviceAndPhone(String device, String phone);

    List<YWJDevice> findByPhone(String phone);
}
