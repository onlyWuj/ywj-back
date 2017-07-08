
package com.zds.scf.biz.common.search;

import com.zds.common.lang.exception.Exceptions;
import org.springframework.util.Assert;

import static com.zds.scf.biz.common.search.SearchCriteria.Operator.NOTNULL;
import static com.zds.scf.biz.common.search.SearchCriteria.Operator.NULL;

/**
 *
 */

public class SearchCriteria {
	private String key;
	private Operator operator;
	private Object value;
	
	public SearchCriteria(String key, String opStr, Object value) {
		Assert.notNull(key);
		Assert.notNull(opStr);
		try {
			this.operator = Enum.valueOf(Operator.class, opStr.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw Exceptions.newRuntimeExceptionWithoutStackTrace("操作符不存在:" + opStr);
		}
		this.key = key;
		if ((value == null) && (!this.operator.equals(NULL) && !this.operator.equals(NOTNULL))) {
			throw Exceptions.newRuntimeExceptionWithoutStackTrace("搜索条件值为空时，只支持NULL或者NOTNULL");
		}
		this.value = value;
	}
	
	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public Operator getOperator() {
		return operator;
	}
	
	public void setOperator(Operator operator) {
		this.operator = operator;
	}
	
	public Object getValue() {
		return value;
	}
	
	public void setValue(Object value) {
		this.value = value;
	}
	
	public enum Operator {
		EQ,
		NEQ,
		LIKE,
		LLIKE,
		RLIKE,
		GT,
		LT,
		GTE,
		LTE,
		IN,
		NOTIN,
		NULL,
		NOTNULL,
	}
}
