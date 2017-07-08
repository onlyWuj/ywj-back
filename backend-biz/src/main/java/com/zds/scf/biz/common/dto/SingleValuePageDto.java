
package com.zds.scf.biz.common.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 *
 */
public class SingleValuePageDto<T> extends PageDto {

    @NotNull
    @Valid
    private T data;

    public static SingleValuePageDto<Long> asLong(Long data) {
        SingleValuePageDto<Long> singleValuePageDto = new SingleValuePageDto<>();
        singleValuePageDto.setData(data);
        return singleValuePageDto;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
