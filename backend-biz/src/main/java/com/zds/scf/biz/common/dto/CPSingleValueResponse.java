
package com.zds.scf.biz.common.dto;

import com.zds.common.lang.beans.Copier;
import com.zds.common.lang.exception.Exceptions;
import com.zds.common.lang.result.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 */
@SuppressWarnings("all")
public class CPSingleValueResponse<T> extends CPResponse {

    private T data;

    public T getData() {
        return data;
    }

    /**
     * 响应成功时获取数据，否则抛出失败
     */
    public T data() {
        if (this.isFail()) {
            throw Exceptions.newRuntimeExceptionWithoutStackTrace(this.getDescription());
        }
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    private Object extraData;//扩展参数

    public Object getExtraData() {
        return extraData;
    }

    public void setExtraData(Object extraData) {
        this.extraData = extraData;
    }

    /**
     * 创建只包含一个有效结果的CPResponse，设置状态为{@link Status#SUCCESS}
     *
     * @param playload 参数值
     * @param <T>      参数类型
     * @return SingleValueResult<T>
     */
    public static <T> CPSingleValueResponse<T> from(T playload) {
        CPSingleValueResponse<T> response = new CPSingleValueResponse<>();
        if (playload == null) {
            response.setStatus(Status.FAIL);
        } else {
            response.setData(playload);
            response.setStatus(Status.SUCCESS);
        }
        return response;
    }

    /**
     * 把当前对象转换为CPViewResultInfo对象
     */
    public CPViewResultInfo to() {
        return CPViewResultInfo.form(this);
    }

    /**
     * 把当前对象转换为CPViewResultInfo对象,其中data对象类型为toClass
     */
    public <T1 extends Object> CPViewResultInfo to(Class<T1> toClass) {
        CPViewResultInfo viewResultInfo = new CPViewResultInfo();
        viewResultInfo.setMessage(this.getDescription());
        viewResultInfo.setSuccess(this.getStatus() == Status.SUCCESS);
        viewResultInfo.setCode(this.getCode());
        if (this.getData() != null) {
            if (this.getData().getClass().isArray()) {
                Object[] arr = (Object[]) this.getData();
                List list = new ArrayList(arr.length);
                for (Object o : arr) {
                    list.add(Copier.copy(o, toClass, Copier.CopyStrategy.IGNORE_NULL));
                }
                viewResultInfo.setData(list);
            } else if (Page.class.isAssignableFrom(this.getData().getClass())) {
                Page page = (Page) this.getData();
                Page newPage = page.map(source -> Copier.copy(source, toClass, Copier.CopyStrategy.IGNORE_NULL));
                viewResultInfo.setData(new CPViewResultInfo.CPPageImpl((PageImpl) newPage, null));
            } else if (Collection.class.isAssignableFrom(this.getData().getClass())) {
                Collection collection = (Collection) this.getData();
                List list = new ArrayList(collection.size());
                for (Object o : collection) {
                    list.add(Copier.copy(o, toClass, Copier.CopyStrategy.IGNORE_NULL));
                }
                viewResultInfo.setData(list);
            } else {
                viewResultInfo.setData(Copier.copy(this.getData(), toClass, Copier.CopyStrategy.IGNORE_NULL));
            }
        }
        return viewResultInfo;
    }

    /**
     * 把当前对象转换为CPViewResultInfo对象,其中data对象类型为toClass,扩展分页参数extraData (当前对象必须为分页对象)
     */
    public <T1 extends Object> CPViewResultInfo to(Class<T1> toClass, Object extraData) {
        CPViewResultInfo viewResultInfo = new CPViewResultInfo();
        viewResultInfo.setMessage(this.getDescription());
        viewResultInfo.setSuccess(this.getStatus() == Status.SUCCESS);
        viewResultInfo.setCode(this.getCode());
        if (this.getData() != null) {
            if (Page.class.isAssignableFrom(this.getData().getClass())) {
                Page page = (Page) this.getData();
                Page newPage = page.map(source -> Copier.copy(source, toClass, Copier.CopyStrategy.IGNORE_NULL));
                viewResultInfo.setData(new CPViewResultInfo.CPPageImpl((PageImpl) newPage, extraData));
            }
        }
        return viewResultInfo;
    }
}
