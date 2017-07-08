package com.zds.scf.biz.ywj.domain.entity.report;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/21.
 */
public class BindNumber implements Serializable {
    private Integer total;
    private Integer bindNumber;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getBindNumber() {
        return bindNumber;
    }

    public void setBindNumber(Integer bindNumber) {
        this.bindNumber = bindNumber;
    }
}
