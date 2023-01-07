package com.myprojects.myportfolio.clients.general.specifications;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static com.myprojects.myportfolio.clients.utils.UtilsConstants.DATE_FORMAT;
import static com.myprojects.myportfolio.clients.utils.UtilsConstants.DATE_TIME_FORMAT;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoreSpecification<T>  implements Specification<T> {

    private QueryDTO criteria;

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        try {

            // greaterThanOrEqualTo
            if (criteria.getOperation().equalsIgnoreCase(">")) {

                // LocalDateTime
                if (root.get(criteria.getKey()).getJavaType() == LocalDateTime.class) {
                    return this.checkDates(root, criteriaBuilder, criteria.getOperation());

                // EveryThing else
                } else {
                    return criteriaBuilder.greaterThanOrEqualTo(
                            root.<String> get(criteria.getKey()), criteria.getValue().toString());
                }
            }

            // lessThanOrEqualTo
            else if (criteria.getOperation().equalsIgnoreCase("<")) {

                // LocalDateTime
                if (root.get(criteria.getKey()).getJavaType() == LocalDateTime.class) {
                    return this.checkDates(root, criteriaBuilder, criteria.getOperation());

                // EveryThing else
                } else {
                    return criteriaBuilder.lessThanOrEqualTo(
                            root.<String> get(criteria.getKey()), criteria.getValue().toString());
                }
            }

            // equal
            else if (criteria.getOperation().equalsIgnoreCase(":")) {

                // String in like
                if (root.get(criteria.getKey()).getJavaType() == String.class) {

                    String newValue = ((String) criteria.getValue()).replace("*", "%");
                    return criteriaBuilder.like(
                            root.<String>get(criteria.getKey()), newValue);

                // LocalDateTime
                } else if (root.get(criteria.getKey()).getJavaType() == LocalDateTime.class) {
                    return this.checkDates(root, criteriaBuilder, criteria.getOperation());

                // Boolean
                } else if (root.get(criteria.getKey()).getJavaType() == Boolean.class) {
                    if(criteria.getValue().equals("true")) {
                        return criteriaBuilder.isTrue(root.get(criteria.getKey()));
                    } else {
                        return criteriaBuilder.isFalse(root.get(criteria.getKey()));
                    }

                // EveryThing else
                } else {
                    return criteriaBuilder.equal(root.get(criteria.getKey()), criteria.getValue());
                }
            }

            // notEqual
            else if (criteria.getOperation().equalsIgnoreCase("!")) {

                // String in like
                if (root.get(criteria.getKey()).getJavaType() == String.class) {

                    String newValue = ((String) criteria.getValue()).replace("*", "%");
                    return criteriaBuilder.notLike(
                            root.<String>get(criteria.getKey()), newValue);

                // LocalDateTime
                } else if (root.get(criteria.getKey()).getJavaType() == LocalDateTime.class) {
                    return this.checkDates(root, criteriaBuilder, criteria.getOperation());

                // Boolean
                } else if (root.get(criteria.getKey()).getJavaType() == Boolean.class) {
                    if(criteria.getValue().equals("true")) {
                        return criteriaBuilder.isFalse(root.get(criteria.getKey()));
                    } else {
                        return criteriaBuilder.isTrue(root.get(criteria.getKey()));
                    }

                // EveryThing else
                } else {
                    return criteriaBuilder.notEqual(root.get(criteria.getKey()), criteria.getValue());
                }
            }


            return null;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private Predicate checkDates(Root<T> root, CriteriaBuilder criteriaBuilder, String operation) throws ParseException {
        LocalDateTime localDateTime = (LocalDateTime) criteria.getValue();
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        Expression<Date> dateExpr;
        Predicate predicate = null;

        // Check if we need to compare truncated dates
        if(localDateTime.getHour()==0 && localDateTime.getMinute()==0 && localDateTime.getSecond()==0){
            dateExpr = criteriaBuilder.function("DATE", Date.class, root.get(criteria.getKey())).as(Date.class);
        } else {
            dateExpr = criteriaBuilder.function("", Date.class, root.get(criteria.getKey())).as(Date.class);
        }

        switch (operation){
            case ">":
                predicate = criteriaBuilder.greaterThanOrEqualTo(dateExpr, date);
                break;
            case "<":
                predicate = criteriaBuilder.lessThanOrEqualTo(dateExpr, date);
                break;
            case ":":
                predicate = criteriaBuilder.equal(dateExpr, date);
                break;
            case "!":
                predicate = criteriaBuilder.notEqual(dateExpr, date);
                break;
        }

        return predicate;

    }

}
