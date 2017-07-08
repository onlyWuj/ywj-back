package com.zds.scf.web.common.model;

import java.util.List;

/**
 * @author wuxiang@zdsoft.cn
 */
public class IdModel {

    private Long id;

    private List<Long> ids;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }
}
