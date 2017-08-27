
package com.zds.scf.biz.common.search;

import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.zds.common.lang.exception.Exceptions;
import org.springframework.data.jpa.domain.Specifications;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

/**
 *
 */
public class SpecificationsBuilder {
	
	private static final char AND_SPLITTOR = ' ';
	private static final char AND_JOINER = '+';
	private static final char OR_SPLITTOR = '|';
	private static final char VALUE_SPLITTOR = ':';
	private static final char OP_SPLITTOR = '_';
	
	private List<SearchCriteria> params = Lists.newArrayList();
	private boolean and = true;
	
	public SpecificationsBuilder with(String key, String operation, Object value) {
		params.add(new SearchCriteria(key, operation, value));
		return this;
	}
	public SpecificationsBuilder with(String key, SearchCriteria.Operator operator, Object value) {
		params.add(new SearchCriteria(key, operator.toString(), value));
		return this;
	}
	
	public SpecificationsBuilder and() {
		and = true;
		return this;
	}
	
	public SpecificationsBuilder or() {
		and = false;
		return this;
	}

	/**
	 * 构建url查询参数
	 * @return
	 */
	public String buildParam() {
		StringBuilder sb = new StringBuilder();
		char joiner = and ? AND_JOINER : OR_SPLITTOR;
		if (params.size() == 0) {
			return "";
		} else {
			for (SearchCriteria param : params) {
				sb.append(param.getKey()).append(OP_SPLITTOR).append(param.getOperator().toString().toLowerCase());
				if (param.getValue() != null) {
					sb.append(VALUE_SPLITTOR);
					try {
						sb.append(URLEncoder.encode(param.getValue().toString(), Charsets.UTF_8.toString()));
					} catch (UnsupportedEncodingException e) {
						throw Exceptions.newRuntimeExceptionWithoutStackTrace("字符集错误，表达式值字符集应该为utf-8");
					}
				}
				sb.append(joiner);
			}
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}
	
	public Specifications build() {
		if (params.size() == 0) {
			return Specifications.where(null);
		} else {
			Specifications specifications = null;
			for (int i = 0; i < params.size(); i++) {
				if (i == 0) {
					specifications = Specifications.where(new EntitySpecification(params.get(i)));
				} else {
					if (and) {
						specifications = specifications.and(new EntitySpecification(params.get(i)));
					} else {
						specifications = specifications.or(new EntitySpecification(params.get(i)));
					}
				}
			}
			return specifications;
		}
	}
	
	public void parse(String searchStr) {
		if (searchStr != null) {
			char criteriaSplitter = AND_SPLITTOR;
			if (searchStr.indexOf(OR_SPLITTOR) > -1) {
				criteriaSplitter = OR_SPLITTOR;
				and = false;
			}
			
			List<String> criteriaStrs = Splitter.on(criteriaSplitter).trimResults().splitToList(searchStr);
			for (String criteriaStr : criteriaStrs) {
				List<String> criteria = Splitter.on(VALUE_SPLITTOR).trimResults().splitToList(criteriaStr);
				List<String> cond = Splitter.on(OP_SPLITTOR).trimResults().splitToList(criteria.get(0));
				if (cond.size() != 2) {
					throw Exceptions.newRuntimeExceptionWithoutStackTrace("搜索条件表达式语法错误:" + criteria.get(0));
				} else {
					String field = cond.get(0);
					String op = cond.get(1);
					String value = null;
					if (criteria.size() == 2) {
						try {
							value = URLDecoder.decode(criteria.get(1), Charsets.UTF_8.toString());
						} catch (UnsupportedEncodingException e) {
							throw Exceptions.newRuntimeExceptionWithoutStackTrace("字符集错误，表达式值字符集应该为utf-8");
						}
					}
					with(field, op, value);
				}
			}
		}
	}
	
}
