
package com.zds.scf.biz.common;

import java.io.Serializable;

/**
 *
 */
public class CPContext {
    private static final ThreadLocal<CPContext> LOCAL = new ThreadLocal<CPContext>() {
        protected CPContext initialValue() {
            return new CPContext();
        }
    };

    private String gid;

    private UserInfo userInfo;

    private CPContext() {

    }

    public static CPContext getContext() {
        return LOCAL.get();
    }

    public static void putContext(CPContext cpContext) {
        LOCAL.set(cpContext);
    }

    public static void removeContext() {
        LOCAL.remove();
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    /**
     * 用户信息
     */
    public static class UserInfo implements Serializable {
        private Long id;
        private String code;
        private String name;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
