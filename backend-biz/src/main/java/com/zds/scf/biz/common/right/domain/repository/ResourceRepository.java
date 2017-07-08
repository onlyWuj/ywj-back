package com.zds.scf.biz.common.right.domain.repository;

import com.zds.scf.biz.common.domain.repository.BaseRepository;
import com.zds.scf.biz.common.right.domain.entity.Resource;
import com.zds.scf.biz.common.right.domain.entity.Role;

import java.util.List;


public interface ResourceRepository extends BaseRepository<Resource, Long> {
    List<Resource> findByIdIn(Long[] rids);
}
