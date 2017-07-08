package com.zds.scf.biz.common.dto;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class SimpleDetailDto implements Serializable {

    //页面上新增的行
    @Valid
    private List<Long> insertList = new ArrayList();


    //页面上删除的行Id
    @Valid
    private List<Long> deleteList = new ArrayList<Long>();

    public List<Long> getInsertList() {
        return insertList;
    }

    public void setInsertList(List<Long> insertList) {
        this.insertList = insertList;
    }


    public List<Long> getDeleteList() {
        return deleteList;
    }

    public void setDeleteList(List<Long> deleteList) {
        this.deleteList = deleteList;
    }


}
