
package com.zds.scf.biz.common.dto;

import com.zds.common.util.ToString;

import java.io.Serializable;

/**
 *
 */
public class BaseDto implements Serializable {
    @Override
    public String toString() {
        return ToString.toString(this);
    }
}