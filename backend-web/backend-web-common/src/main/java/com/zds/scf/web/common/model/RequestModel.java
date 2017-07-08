/*
 *
 * Copyright (c) 2016 All Rights Reserved
 */

/*
 * 修订记录:
 * 2016-10-11 17:24 创建
 */
package com.zds.scf.web.common.model;

import com.zds.common.lang.dto.DtoBase;
import com.zds.common.lang.validator.Validators;

/**
 *
 */
public class RequestModel extends DtoBase {

    public void check() {
        Validators.checkJsr303(this);
    }

    public void checkWithGourp(Class<?>... groups) {
        Validators.checkJsr303(this, groups);
    }
}
