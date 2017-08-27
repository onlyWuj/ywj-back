
package com.zds.scf.biz.common.domain.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.zds.common.lang.beans.Copier;
import com.zds.common.lang.exception.Exceptions;
import com.zds.common.spring.ApplicationContextHolder;
import com.zds.common.util.ToString;
import com.zds.scf.biz.common.udc.UDC;
import com.zds.scf.biz.common.udc.UDCUserType;
import com.zds.scf.biz.common.util.Pages;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.data.domain.Page;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 */
@MappedSuperclass
@TypeDefs({@TypeDef(defaultForType = UDC.class, name = "udcType", typeClass = UDCUserType.class),})
@SuppressWarnings("all")
public class BaseEntity implements Serializable {


    public static final Long NULL_MERCHANT_ID = 1L;

    private static volatile AutowiredAnnotationBeanPostProcessor autowiredAnnotationBeanPostProcessor = null;
    private static boolean inited = false;

    public BaseEntity() {
        if (inited) {
            autowire();
        }
    }

    /**
     * 注入Entity中的@Autowired
     */
    private void autowire() {
        if (autowiredAnnotationBeanPostProcessor == null) {
            synchronized (AggEntity.class) {
                if (autowiredAnnotationBeanPostProcessor == null) {
                    List<BeanPostProcessor>
                            beanPostProcessors = ((AbstractBeanFactory) (((AbstractApplicationContext) ApplicationContextHolder.get()).getBeanFactory())).getBeanPostProcessors();
                    for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
                        if (beanPostProcessor instanceof AutowiredAnnotationBeanPostProcessor && beanPostProcessor.getClass().getName().contains("AutowiredAnnotationBeanPostProcessor")) {
                            autowiredAnnotationBeanPostProcessor = (AutowiredAnnotationBeanPostProcessor) beanPostProcessor;
                        }
                    }
                }
            }
        }
        autowiredAnnotationBeanPostProcessor.postProcessPropertyValues(null, null, this, getClass().getName());
    }

    public static void inited() {
        inited = true;
    }

    @Id
    @GeneratedValue
    @Column(name = "id", columnDefinition = "int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键'")
    protected Long id;





    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 从DTO拷贝属性到领域对象
     * <p>
     * 拷贝策略为：1.忽略DTO中的null 2. 忽略DTO属性不兼容
     *
     * @param dto 传输对象
     */
    public void from(Object dto) {
        this.from(dto,null);
    }

    public void from(Object dto,String... ignorePropeties) {
        Copier.copy(dto, this, Copier.CopyStrategy.IGNORE_NULL, Copier.NoMatchingRule.IGNORE,ignorePropeties);
    }

    public void fromContainNUll(Object dto) {
        Copier.copy(dto, this, Copier.CopyStrategy.CONTAIN_NULL, Copier.NoMatchingRule.EXCEPTION);
    }

    /**
     * 转换领域对象为目标类型
     *
     * @param clazz 目标类型
     */
    public <T> T to(Class<T> clazz,String... ignorePropeties) {
        try {
            T t = clazz.newInstance();
            Copier.copy(this, t, Copier.CopyStrategy.IGNORE_NULL, Copier.NoMatchingRule.IGNORE,ignorePropeties);
            return t;
        } catch (Exception e) {
            throw Exceptions.newRuntimeException(e);
        }
    }

    /**
     * 把实体对象list转换为目标对象List
     */
    public static <T, S extends BaseEntity> List<T> map(List<S> list, Class<T> clazz,String... ignorePropeties) {
        if (list == null || list.isEmpty()) {
            return Lists.newArrayList();
        }
        List<T> ts = new ArrayList<>(list.size());
        for (S s : list) {
            ts.add(s.to(clazz,ignorePropeties));
        }
        return ts;
    }

    /**
     * 转换Page对象中的集合类型S为T
     */
    public static <S extends BaseEntity, T> Page<T> map(Page<S> page, Class<T> clazz) {
        return Pages.map(page, clazz);
    }

    /**
     * 把实体对象Set转换为目标对象Set
     */
    public static <T, S extends BaseEntity> Set<T> map(Set<S> set, Class<T> clazz) {
        if (set == null || set.isEmpty()) {
            return Sets.newHashSet();
        }
        Set<T> ts = new HashSet<>(set.size());
        for (S s : set) {
            ts.add(s.to(clazz));
        }
        return ts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseEntity)) return false;

        BaseEntity that = (BaseEntity) o;

        return id != null ? id.equals(that.id) : that.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return ToString.toString(this);
    }
}
