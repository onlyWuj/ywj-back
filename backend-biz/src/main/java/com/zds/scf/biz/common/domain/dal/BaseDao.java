
package com.zds.scf.biz.common.domain.dal;

import com.zds.scf.biz.common.domain.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 *
 */
@NoRepositoryBean
public interface BaseDao<T extends BaseEntity, ID extends Serializable> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
}
