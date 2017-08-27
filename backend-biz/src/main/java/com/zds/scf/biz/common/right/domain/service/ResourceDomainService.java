package com.zds.scf.biz.common.right.domain.service;

import com.cp.boot.appservice.stereotype.DomainService;
import com.google.common.collect.Lists;
import com.zds.scf.biz.common.right.domain.entity.Resource;
import com.zds.scf.biz.common.right.domain.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@DomainService
public class ResourceDomainService {

    @Autowired
    private ResourceRepository repository;

    public List<Resource> list() {
        return repository.findAll(new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.and(criteriaBuilder.notEqual(root.get("url"),"admin"));
            }
        });
    }
}