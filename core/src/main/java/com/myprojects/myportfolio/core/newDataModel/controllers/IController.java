package com.myprojects.myportfolio.core.newDataModel.controllers;

import com.myprojects.myportfolio.clients.general.messages.Message;
import com.myprojects.myportfolio.clients.general.messages.MessageResource;
import com.myprojects.myportfolio.clients.general.messages.MessageResources;
import com.myprojects.myportfolio.clients.general.specifications.SpecificationsBuilder;
import com.sun.istack.NotNull;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.myprojects.myportfolio.clients.utils.UtilsConstants.DATE_FORMAT;
import static com.myprojects.myportfolio.clients.utils.UtilsConstants.TIME_FORMAT;

public interface IController<R> {

    /* CONSTANTS */
    String FILTERS = "filters";
    String filterKey = "(\\w.+?)";
    String filterOperation = "([:!<>])";
    String filterValue = "(.+)";
    String filtersSeparator = ",";


    /* METHODS */
    ResponseEntity<MessageResources<R>> find(String filters, Pageable pageable) throws Exception;

    ResponseEntity<MessageResource<R>> get(Integer id) throws Exception;

    ResponseEntity<MessageResource<R>> create(R resource) throws Exception;

    ResponseEntity<MessageResource<R>> update(Integer id, R resource) throws Exception;

    ResponseEntity<MessageResource<R>> delete(Integer id) throws Exception;


    /* FILTERS AND VIEW */
    /* ATTENTION: Setting the view as a request attribute is useful to retrieve it wherever you need without passing it throughout every method,
     *             BUT, It works only in the same thread! */
    default <T> Specification<T> defineFilters(String filters) {
        SpecificationsBuilder<T> builder = new SpecificationsBuilder<>();
        if (Strings.isNotBlank(filters)) {
            String[] filtersArray = filters.split(filtersSeparator);
            Pattern pattern = Pattern.compile(filterKey + filterOperation + filterValue);

            for (String filter : filtersArray) {
                Matcher matcher = pattern.matcher(filter);
                while (matcher.find()) {

                    String key = matcher.group(1);
                    String operation = matcher.group(2);
                    String value = matcher.group(3);
                    Object valueObj = value;

                    try {
                        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                                .appendPattern(DATE_FORMAT)
                                .optionalStart()
                                .appendPattern(" " + TIME_FORMAT)
                                .optionalEnd()
                                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                                .toFormatter();
                        valueObj = LocalDateTime.parse(value, formatter);
                    } catch (Exception e) {
                        // Value is not a date
                    }

                    builder.with(key, operation, valueObj);
                }
            }
        }

        return builder.build();
    }




    /* RESPONSES */

    /* Build Success Response for single Entity */
    default ResponseEntity<MessageResource<R>> buildSuccessResponse(R element) {
        return this.buildSuccessResponse(element, new ArrayList<>());
    }

    default ResponseEntity<MessageResource<R>> buildSuccessResponse(@NotNull R element, List<Message> messages) {
        MessageResource<R> result = new MessageResource<>(element, messages);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /* Build Success Response for Slice of Entities */
    default ResponseEntity<MessageResources<R>> buildSuccessResponses(Slice<R> slice) {
        return this.buildSuccessResponses(slice, new ArrayList<>());
    }

    default ResponseEntity<MessageResources<R>> buildSuccessResponses(@NotNull Slice<R> slice, List<Message> messages) {

        HttpHeaders headers = new HttpHeaders();
        headers.put("IS-EMPTY", List.of("" + slice.isEmpty()));
        headers.put("IS-LAST", List.of("" + slice.isLast()));
        headers.put("NUMBER", List.of("" + slice.getNumberOfElements()));

        MessageResources<R> result = new MessageResources<>(slice.getContent(), messages);
        return new ResponseEntity<>(result, headers, HttpStatus.OK);
    }

    /* Build Success Response for List of object */
    default <T> ResponseEntity<MessageResources<T>> buildSuccessResponses(List<T> list) {
        return this.buildSuccessResponses(list, new ArrayList<>());
    }

    default <T> ResponseEntity<MessageResources<T>> buildSuccessResponses(@NotNull List<T> list, List<Message> messages) {
        HttpHeaders headers = new HttpHeaders();
        headers.put("IS-EMPTY", List.of("" + list.isEmpty()));
        headers.put("IS-LAST", List.of("" + true));
        headers.put("NUMBER", List.of("" + list.size()));

        MessageResources<T> result = new MessageResources<>(list, messages);
        return new ResponseEntity<>(result, headers, HttpStatus.OK);
    }

    default String fieldMissing(String field) {
        return "Mandatory parameter is missing: " + field;
    }

    default String resourceMissing() {
        return "No valid resource was provided.";
    }

    default String noEntityFound(Integer id) {
        return "No valid entity found with id: " + id;
    }

}
