package com.zds.scf.biz.common.right.domain.service;

import com.cp.boot.appservice.stereotype.DomainService;
import com.google.common.collect.Lists;
import com.zds.scf.biz.common.CPBusinessException;
import com.zds.scf.biz.common.right.app.dto.role.RoleDto;
import com.zds.scf.biz.common.right.app.dto.role.RoleListDto;
import com.zds.scf.biz.common.right.domain.entity.Role;
import com.zds.scf.biz.common.right.domain.repository.RoleRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Objects;

@DomainService
public class RoleDomainService {

    @Autowired
    private RoleRepository repository;
    public Role create(RoleDto request) {
        Role role = new Role();
        role.from(request);
        role = repository.save(role);
        return role;
    }

    @CachePut(value ="Role",key = "#request.id")
    public Role update(RoleDto request) {
        //1. 加载领域对象
        Role role = repository.findOne(request.getId());
        if (role == null) {
            CPBusinessException.throwIt("id", "不存在");
        } else {
            role.from(request);
        }
        return role;
    }

    @CachePut(value ="user",key = "#id")
    public void disable(Long id) {
        //1. 加载领域对象
        Role role = repository.findOne(id);
        if (role == null) {
            CPBusinessException.throwIt("id", "不存在");
        }
        role.setAvailable(false);
    }
    @CachePut(value ="user",key = "#id")
    public void enable(Long id) {
        //1. 加载领域对象
        Role role = repository.findOne(id);
        if (role == null) {
            CPBusinessException.throwIt("id", "不存在");
        }
        role.setAvailable(true);

    }

    @Cacheable(value = "Role",key = "#id")
    public Role load(Long id) {
        return repository.findOne(id);
    }

    public Page<Role> pagination(RoleListDto listParamDto) {
        return repository.findAll((Specification<Role>) listParamDto.createSpecification(), listParamDto.toPage());
    }


    public List<Role> list() {
        return repository.findAll(new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> predicates = Lists.newArrayListWithCapacity(2);
                predicates.add(cb.notEqual(root.get("code"), "admin"));
                predicates.add(cb.equal(root.get("available"),true));
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        });
    }
}