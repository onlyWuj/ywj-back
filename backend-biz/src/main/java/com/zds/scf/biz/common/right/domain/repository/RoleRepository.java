package com.zds.scf.biz.common.right.domain.repository;

import com.zds.scf.biz.common.domain.repository.BaseRepository;
import com.zds.scf.biz.common.right.domain.entity.Role;

import java.util.List;


public interface RoleRepository extends BaseRepository<Role, Long> {
    List<Role> findByIdIn(Long[] ids);
}
