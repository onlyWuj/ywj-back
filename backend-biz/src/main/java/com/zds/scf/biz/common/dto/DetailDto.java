package com.zds.scf.biz.common.dto;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class DetailDto<T extends BaseDto> implements Serializable {

    //页面上新增的行
    @Valid
    private List<T> insertList = new ArrayList<T>();

    //页面上修改的行
    @Valid
    private List<T> updateList = new ArrayList<T>();

    //页面上删除的行Id
    @Valid
    private List<Long> deleteList = new ArrayList<Long>();

    public List<T> getInsertList() {
        return insertList;
    }

    public void setInsertList(List<T> insertList) {
        this.insertList = insertList;
    }

    public List<T> getUpdateList() {
        return updateList;
    }

    public void setUpdateList(List<T> updateList) {
        this.updateList = updateList;
    }

    public List<Long> getDeleteList() {
        return deleteList;
    }

    public void setDeleteList(List<Long> deleteList) {
        this.deleteList = deleteList;
    }

    public void addInsert(T t) {
        insertList.add(t);
    }

    public void addUpdate(T t) {
        updateList.add(t);
    }

    public void addDelete(Long id) {
        deleteList.add(id);
    }

    public void addDelete(List<Long> ids) {
        deleteList.addAll(ids);
    }
}
