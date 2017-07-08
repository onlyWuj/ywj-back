package com.zds.scf.biz.pbac.service;


import com.zds.scf.biz.pbac.domain.dao.SeResourceDao;
import com.zds.scf.biz.pbac.domain.dao.SeRoleDao;
import com.zds.scf.biz.pbac.domain.dao.SeUserDao;
import com.zds.scf.biz.pbac.domain.entity.SeResource;
import com.zds.scf.biz.pbac.domain.entity.SeRole;
import com.zds.scf.biz.pbac.domain.entity.SeUser;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class SeResourceService {

    @Autowired
    private SeResourceDao seResourceDao;

    @Autowired
    private SeUserDao seUserDaoDao;

    @Autowired
    private SeRoleDao seRoleDao;
    @Autowired
    private RedisTemplate<String, SeResource> redisTemplate;

    @Deprecated
    public SeResource createResource(SeResource resource) {
        return seResourceDao.createResource(resource);
    }
    @Deprecated
    public SeResource updateResource(SeResource resource) {
        redisTemplate.delete("SeResource." + resource.getId());
        return seResourceDao.updateResource(resource);
    }
    @Deprecated
    public void deleteResource(Long resourceId) {
        redisTemplate.delete("SeResource." + resourceId);
        seResourceDao.deleteResource(resourceId);
    }

    public SeResource findOne(Long resourceId) {
        SeResource seResource = redisTemplate.opsForValue().get("SeResource." + resourceId);
        if (seResource == null) {
            seResource = seResourceDao.findOne(resourceId);
            if (seResource != null) {
                redisTemplate.opsForValue().set("SeResource." + resourceId, seResource);
            }
        }
        return seResource;
    }


    public List<SeResource> findByBusinessType(String type) {
        return seResourceDao.findByBusinessType(type);
    }

    public  List<SeResource> findByUser(SeUser seUser){
        Set<Long> resourceIds = new HashSet<Long>();
        for (Long roleId : seUser.getRoleIds()) {
            SeRole role =seRoleDao.findOne(roleId);
            if (role != null) {
                resourceIds.addAll(role.getResourceIds());
            }
        }
        return seResourceDao.findByIdIn(resourceIds);
    }

    public  List<SeResource> findByUser(Long userId){
        SeUser seUser = seUserDaoDao.findOne(userId);
        return findByUser(seUser);
    }

    public Set<String> findPermissions(Set<Long> resourceIds) {
        Set<String> permissions = new HashSet<String>();
        for (Long resourceId : resourceIds) {
            SeResource resource = findOne(resourceId);
            if (resource != null && !StringUtils.isEmpty(resource.getPermission())) {
                permissions.add(resource.getPermission());
            }
        }
        return permissions;
    }

    public List<SeResource> findByUserAndType(Long userId, SeResource.ResourceType resourceType){
        List<SeResource> seResources=findByUser(userId);
        List result = new ArrayList<>();
        for(SeResource resource : seResources){
            if(resource.getType()==resourceType){
                result.add(resource);
            }
        }
        return result;

    }
    public List<SeResource> findAll(){
        return seResourceDao.findAll();
    }


}
