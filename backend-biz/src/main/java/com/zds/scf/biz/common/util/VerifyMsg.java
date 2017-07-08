package com.zds.scf.biz.common.util;

import com.zds.common.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class VerifyMsg {

    private static final String DEFAULT_SPLITOR = "、";

    private List<String> msgList = new ArrayList<>();

    public boolean isNotEmpty() {
        return msgList.size() > 0;
    }

    public void addMsg(String str) {
        msgList.add(str);
    }

    public String getMsg(String splitor) {
        splitor = StringUtils.checkEmptyString(splitor, DEFAULT_SPLITOR);
        StringBuilder msgBuilder = new StringBuilder(256).append("");
        boolean isFirst = true;
        for (String msg : msgList) {
            msgBuilder.append(isFirst ? "" : splitor).append(msg);
            isFirst = false;
        }
        return msgBuilder.toString();
    }

    public String getMsg() {
        return getMsg(DEFAULT_SPLITOR);
    }
}
