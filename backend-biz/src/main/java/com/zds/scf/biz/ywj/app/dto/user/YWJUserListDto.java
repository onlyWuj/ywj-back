package com.zds.scf.biz.ywj.app.dto.user;
import com.google.common.collect.Lists;
import com.zds.common.util.StringUtils;
import com.zds.scf.biz.common.dto.BaseDto;
import com.zds.scf.biz.common.dto.PageDto;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.List;


public class YWJUserListDto extends PageDto {

    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Specification<?> createSpecification() {
        if (StringUtils.isBlank(phone) ) {
            return null;
        }

        Specification<?> typeSpecification = (root, query, cb) -> {
            List<Predicate> predicates = Lists.newArrayListWithCapacity(1);
            if (StringUtils.isNotBlank(this.getPhone())) {
                predicates.add(cb.like(root.get("phone"), "%" + this.getPhone() + "%"));
            }
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };

        return typeSpecification;
    }
}

