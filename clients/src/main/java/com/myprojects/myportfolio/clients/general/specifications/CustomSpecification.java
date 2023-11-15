package com.myprojects.myportfolio.clients.general.specifications;

import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomSpecification<T> implements Specification<T> {

    private QueryDTO criteria;

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        try {

            // If the key contains a space, we need to apply the filters to all the fields
            String[] fields = criteria.getKey().split("\\s+");

            List<Predicate> orPredicates = new ArrayList<>();
            for (String field : fields) {
                orPredicates.add(applyFiltersToField(root, criteriaBuilder, field));
            }

            return criteriaBuilder.or(orPredicates.toArray(new Predicate[0]));

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**********************/
    /*** Private Methods **/
    /**********************/

    private Predicate applyFiltersToField(Root<T> root, CriteriaBuilder criteriaBuilder, String field) throws ParseException {
        Expression<T> rootExpression;

        if (field.contains(".")) {
            String childTable = field.substring(0, field.indexOf("."));
            String childField = field.substring(field.indexOf(".") + 1);
            rootExpression = colJoin(root, childTable).get(childField);
        } else {
            rootExpression = root.get(field);
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

                String[] valueParts = ((String) criteria.getValue()).split("\\*");
                List<Predicate> orPredicates = new ArrayList<>();
                for (String valuePart : valueParts) {
                    if (!valuePart.isEmpty()) {
                        orPredicates.add(criteriaBuilder.like(rootExpression.as(String.class), "%" + valuePart + "%"));
                    }
                }

                return criteriaBuilder.or(orPredicates.toArray(new Predicate[0]));

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

                String[] valueParts = ((String) criteria.getValue()).split("\\*");
                List<Predicate> orPredicates = new ArrayList<>();
                for (String valuePart : valueParts) {
                    if (!valuePart.isEmpty()) {
                        orPredicates.add(criteriaBuilder.notLike(rootExpression.as(String.class), "%" + valuePart + "%"));
                    }
                }

                return criteriaBuilder.or(orPredicates.toArray(new Predicate[0]));

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
    }

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
