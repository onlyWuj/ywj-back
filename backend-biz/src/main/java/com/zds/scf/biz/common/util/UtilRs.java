package com.zds.scf.biz.common.util;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * 包装处理ResultSet对象；
 *
 */

@SuppressWarnings("UnusedDeclaration")
public class UtilRs {

    private ResultSet rs;

    public UtilRs(ResultSet rs) {
        this.rs = rs;
    }

    public boolean next() throws SQLException {
        return rs.next();
    }


    /**
     * 获取字段内容,同时去掉内容中的"-"字符
     *
     * @param fieldName 字段名称
     * @return 返回值, null会返回""
     * @throws SQLException 异常
     */
    public String getStringRemoveSubSign(String fieldName) throws SQLException {
        String str = rs.getString(fieldName);
        if (str == null)
            return "";
        if ("-".equals(str))
            return "";
        return str;
    }

    /**
     * 获取字段中的字符串类型值,如果为null,则返回"";
     *
     * @param fieldName 字段名称
     * @return 返回值
     * @throws SQLException 从ResultSet获取值时可能抛出的异常
     */
    public String getStringIgnoreNull(String fieldName) throws SQLException {
        String str = rs.getString(fieldName);
        if (str == null)
            return "";
        return str;
    }

    public String getStringIgnoreNull(String fieldName, String defaultvalue) throws SQLException {
        String str = rs.getString(fieldName);
        if (str == null)
            return defaultvalue;
        return str;
    }

    public double getDouble(String fieldName) throws SQLException {
        return rs.getDouble(fieldName);
    }

    public double getDouble(int index) throws SQLException {
        return rs.getDouble(index);
    }

    public double getDoubleNotException(String fieldName) {
        try {
            return rs.getDouble(fieldName);
        } catch (SQLException e) {
            return 0;
        }
    }

    public boolean getBoolean(String fieldName) throws SQLException {
        return rs.getBoolean(fieldName);
    }

    public int getInt(String fieldName) throws SQLException {
        return rs.getInt(fieldName);
    }

    /**
     * 返回int,如果异常则返回0
     *
     * @param fieldName 字段名称
     * @return 不会抛出异常；
     */
    public int getIntNotException(String fieldName) {
        try {
            return rs.getInt(fieldName);
        } catch (SQLException e) {
            return 0;
        }
    }

    public long getLong(String fieldName) throws SQLException {
        return rs.getLong(fieldName);
    }

    public BigDecimal getBigDecimal(String fieldName) {
        try {
            BigDecimal bigDecimal = rs.getBigDecimal(fieldName);
            if (null != bigDecimal)
                return bigDecimal;
            else
                return new BigDecimal(0);
        } catch (SQLException e) {
            return new BigDecimal(0);
        }
    }

    /**
     * 返回long,如果异常则返回0
     *
     * @param fieldName 字段名称
     * @return 不会抛出异常；
     */
    public long getLongNotException(String fieldName) {
        try {
            return rs.getLong(fieldName);
        } catch (SQLException e) {
            return 0;
        }
    }

    public String getLongStr(String fieldName) {
        return String.valueOf(getLongNotException(fieldName));
    }


    public Date getDate(String fieldName) throws SQLException {
        return rs.getDate(fieldName);
    }

    public Timestamp getTimestamp(String fieldName) throws SQLException {
        return rs.getTimestamp(fieldName);
    }

    public int getColumnCount() throws SQLException {
        return rs.getMetaData().getColumnCount();
    }

    public Object getObject(String fieldName) throws SQLException {
        return rs.getObject(fieldName);
    }

    public Object getObject(int index) throws SQLException {
        return rs.getObject(index);
    }
}

