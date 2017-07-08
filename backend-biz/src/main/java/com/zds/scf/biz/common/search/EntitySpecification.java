
package com.zds.scf.biz.common.search;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.Arrays;

/**
 *
 */
public class EntitySpecification<E> implements Specification<E> {
	
//	private static ConversionService conversionService = new DefaultConversionService();
	
	private SearchCriteria criteria;
	
	public EntitySpecification(SearchCriteria criteria) {
		this.criteria = criteria;
	}
	
	@Override
	public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		Path path = root.get(criteria.getKey());
		Predicate predicate = null;
		switch (criteria.getOperator()) {
			case EQ:
				predicate = builder.equal(path, criteria.getValue());
				break;
			case NEQ:
				predicate = builder.notEqual(path, criteria.getValue());
				break;
			case LIKE:
				predicate = builder.like(path, "%" + criteria.getValue() + "%");
				break;
			case LLIKE:
				predicate = builder.like(path, "%" + criteria.getValue());
				break;
			case RLIKE:
				predicate = builder.like(path, criteria.getValue() + "%");
				break;
			case GT:
				predicate = builder.greaterThan(path, (Comparable) criteria.getValue());
				break;
			case GTE:
				predicate = builder.greaterThanOrEqualTo(path, (Comparable) criteria.getValue());
				break;
			case LT:
				predicate = builder.lessThan(path, (Comparable) criteria.getValue());
				break;
			case LTE:
				predicate = builder.lessThanOrEqualTo(path, (Comparable) criteria.getValue());
				break;
			case IN:
				predicate = buildInExpression(path, criteria.getValue().toString());
				break;
			case NOTIN:
				predicate = builder.not(buildInExpression(path, criteria.getValue().toString()));
				break;
			case NULL:
				predicate = builder.isNull(path);
				break;
			case NOTNULL:
				predicate = builder.isNotNull(path);
				break;
		}
		return predicate;
	}
	
	private Predicate buildInExpression(Path root, String value) {
		//		Class clazz = root.getJavaType();
		Predicate predicate;
		String[] values = String.valueOf(value).split(",");
		//		if (clazz == String.class) {
		predicate = root.in(Arrays.asList(values));
		//		}else {
		//			List list=new ArrayList<>();
		//			for (String s : values) {
		//				list.add(conversionService.convert(s,clazz));
		//			}
		//			predicate = root.in(list);
		//		}
		return predicate;
	}
	
}