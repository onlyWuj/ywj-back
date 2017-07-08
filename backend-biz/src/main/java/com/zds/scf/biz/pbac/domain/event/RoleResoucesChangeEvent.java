/*
 *
 * Copyright (c) 2016 All Rights Reserved
 */

/*
 * 修订记录:
 * 2016-10-14 11:28 创建
 */
package com.zds.scf.biz.pbac.domain.event;

/**
 *
 */
public class RoleResoucesChangeEvent {
    public RoleResoucesChangeEvent(Long roleId) {
        this.roleId = roleId;
    }

    private Long roleId;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
