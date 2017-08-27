package com.zds.scf.biz.ywj.domain.repository;


import com.zds.scf.biz.common.domain.repository.BaseRepository;
import com.zds.scf.biz.ywj.domain.entity.YWJUser;

public interface YWJUserRepository extends BaseRepository<YWJUser, Long> {

    public YWJUser findByPhone(String code);

}
