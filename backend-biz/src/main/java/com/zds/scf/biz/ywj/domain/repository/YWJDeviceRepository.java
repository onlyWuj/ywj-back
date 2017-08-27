package com.zds.scf.biz.ywj.domain.repository;


import com.zds.scf.biz.common.domain.repository.BaseRepository;
import com.zds.scf.biz.ywj.domain.entity.device.YWJDevice;

import java.util.List;

public interface YWJDeviceRepository extends BaseRepository<YWJDevice, Long> {

    public List<YWJDevice> findByDevice(String deviceId);

    public YWJDevice findByDeviceAndPhone(String device, String phone);

    public List<YWJDevice> findByPhone(String phone);
}
