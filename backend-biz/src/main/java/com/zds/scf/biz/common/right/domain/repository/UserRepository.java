package com.zds.scf.biz.common.right.domain.repository;


import com.zds.scf.biz.common.domain.repository.BaseRepository;
import com.zds.scf.biz.common.right.domain.entity.User;

public interface UserRepository extends BaseRepository<User, Long> {

    public User findByCode(String code);

}
