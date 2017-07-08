package com.zds.scf.biz.ywj.app.dto.device;
import com.google.common.collect.Lists;
import com.zds.scf.biz.common.dto.BaseDto;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.List;


public class YWJDeviceListDto extends BaseDto {

    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Specification<?> createSpecification() {
        /*if (StringUtils.isBlank(name) && StringUtils.isBlank(code)) {
            return null;
        }*/

        Specification<?> typeSpecification = (root, query, cb) -> {
            List<Predicate> predicates = Lists.newArrayListWithCapacity(5);
        /*    CPContext.UserInfo loginInfo = CPContext.getContext().getUserInfo();
            Integer level = CPContext.getContext().getUserInfo().getLevel().getItemValue();
            predicates.add(cb.equal(root.get("level"), UDC.newUDCWithItemValue("user_level",level+1)));
            predicates.add(cb.equal(root.get("superior"), loginInfo.getId()));
            if (StringUtils.isNotBlank(this.getName())) {
                predicates.add(cb.like(root.get("name"), "%" + this.getName() + "%"));
            }
            if (StringUtils.isNotBlank(this.getCode())) {
                predicates.add(cb.like(root.get("code"), "%" + this.getCode() + "%"));
            }*/
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };

        return typeSpecification;
    }
}
