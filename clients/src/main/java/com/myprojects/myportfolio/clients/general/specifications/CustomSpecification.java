package com.myprojects.myportfolio.clients.general.specifications;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.*;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomSpecification<T> implements Specification<T> {

    private QueryDTO criteria;

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        try {

            Expression<T> rootExpression;

            if (criteria.getKey().contains(".")) {
                String childTable = criteria.getKey().substring(0, criteria.getKey().indexOf("."));
                String childField = criteria.getKey().substring(criteria.getKey().indexOf(".") + 1);
                rootExpression = colJoin(root, childTable).get(childField);
            } else {
                rootExpression = root.get(criteria.getKey());
            }

            // greaterThanOrEqualTo
            if (criteria.getOperation().equalsIgnoreCase(">")) {

                // LocalDateTime
                if (rootExpression.getJavaType() == LocalDateTime.class) {
                    return this.checkDates(root, criteriaBuilder, criteria.getOperation());

                    // EveryThing else
                } else {
                    return criteriaBuilder.greaterThanOrEqualTo(
                            rootExpression.as(String.class), criteria.getValue().toString());
                }
            }

            // lessThanOrEqualTo
            else if (criteria.getOperation().equalsIgnoreCase("<")) {

                // LocalDateTime
                if (rootExpression.getJavaType() == LocalDateTime.class) {
                    return this.checkDates(root, criteriaBuilder, criteria.getOperation());

                    // EveryThing else
                } else {
                    return criteriaBuilder.lessThanOrEqualTo(
                            rootExpression.as(String.class), criteria.getValue().toString());
                }
            }

            // equal
            else if (criteria.getOperation().equalsIgnoreCase(":")) {

                // String in like
                if (rootExpression.getJavaType() == String.class) {

                    String newValue = ((String) criteria.getValue()).replace("*", "%");
                    return criteriaBuilder.like(rootExpression.as(String.class), newValue);

                    // LocalDateTime
                } else if (rootExpression.getJavaType() == LocalDateTime.class) {
                    return this.checkDates(root, criteriaBuilder, criteria.getOperation());

                    // Boolean
                } else if (rootExpression.getJavaType() == Boolean.class) {
                    if (criteria.getValue().equals("true")) {
                        return criteriaBuilder.isTrue(rootExpression.as(Boolean.class));
                    } else {
                        return criteriaBuilder.isFalse(rootExpression.as(Boolean.class));
                    }

                    // EveryThing else
                } else {
                    return criteriaBuilder.equal(rootExpression, criteria.getValue());
                }
            }

            // notEqual
            else if (criteria.getOperation().equalsIgnoreCase("!")) {

                // String in like
                if (rootExpression.getJavaType() == String.class) {

                    String newValue = ((String) criteria.getValue()).replace("*", "%");
                    return criteriaBuilder.notLike(rootExpression.as(String.class), newValue);

                    // LocalDateTime
                } else if (rootExpression.getJavaType() == LocalDateTime.class) {
                    return this.checkDates(root, criteriaBuilder, criteria.getOperation());

                    // Boolean
                } else if (rootExpression.getJavaType() == Boolean.class) {
                    if (criteria.getValue().equals("true")) {
                        return criteriaBuilder.isFalse(rootExpression.as(Boolean.class));
                    } else {
                        return criteriaBuilder.isTrue(rootExpression.as(Boolean.class));
                    }

                    // EveryThing else
                } else {
                    return criteriaBuilder.notEqual(rootExpression, criteria.getValue());
                }
            }


            return null;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /*public Specification<T> equalsToSpecification(@NonNull String field, @NonNull String value) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(field), value);
    }*/

    /**********************/
    /*** Private Methods **/
    /**********************/
    private Predicate checkDates(Expression<?> rootExpression, CriteriaBuilder criteriaBuilder, String operation) throws ParseException {
        LocalDateTime localDateTime = (LocalDateTime) criteria.getValue();
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        Expression<Date> dateExpr;
        Predicate predicate = null;

        // Check if we need to compare truncated dates
        if (localDateTime.getHour() == 0 && localDateTime.getMinute() == 0 && localDateTime.getSecond() == 0) {
            dateExpr = criteriaBuilder.function("DATE", Date.class, rootExpression).as(Date.class);
        } else {
            dateExpr = criteriaBuilder.function("", Date.class, rootExpression).as(Date.class);
        }

        switch (operation) {
            case ">" -> predicate = criteriaBuilder.greaterThanOrEqualTo(dateExpr, date);
            case "<" -> predicate = criteriaBuilder.lessThanOrEqualTo(dateExpr, date);
            case ":" -> predicate = criteriaBuilder.equal(dateExpr, date);
            case "!" -> predicate = criteriaBuilder.notEqual(dateExpr, date);
        }

        return predicate;

    }

    private <R> Join<T, R> colJoin(Root<T> root, String child) {
        return root.join(child);
    }

}
