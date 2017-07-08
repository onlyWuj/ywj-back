
package com.zds.scf.biz.common.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 *
 */
public class CPSingleValueRequest<T> extends CPRequest {

    @NotNull
    @Valid
    private T data;

    public CPSingleValueRequest() {
    }

    public CPSingleValueRequest(T data) {
        this.data = data;
    }

    public static <T> CPSingleValueRequest<T> from(T t) {
        return new CPSingleValueRequest<>(t);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
