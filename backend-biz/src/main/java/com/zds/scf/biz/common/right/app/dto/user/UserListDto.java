package com.zds.scf.biz.common.right.app.dto.user;
import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.collect.Lists;
import com.zds.scf.biz.common.CPContext;
import com.zds.scf.biz.common.dto.BaseDto;
import com.zds.scf.biz.common.dto.PageDto;
import com.zds.scf.biz.common.dto.SimpleDetailDto;
import com.zds.scf.biz.common.udc.UDC;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;


public class UserListDto extends PageDto {

    private String name;

    private String code;

    private Boolean available ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Specification<?> createSpecification() {
        if (StringUtils.isBlank(name) && Objects.isNull(code)&& Objects.isNull(available)) {
            return null;
        }

        Specification<?> typeSpecification = (root, query, cb) -> {
            List<Predicate> predicates = Lists.newArrayListWithCapacity(4);

            if (StringUtils.isNotBlank(this.getName())) {
                predicates.add(cb.like(root.get("name"), "%" + this.getName() + "%"));
            }
            if (!Objects.isNull(this.getCode())) {
                predicates.add(cb.like(root.get("code"), "%" + this.getCode() + "%"));

            }
            if (!Objects.isNull(this.getAvailable())) {
                predicates.add(cb.equal(root.get("available"),this.getAvailable()));
            }
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        return typeSpecification;
    }
}
