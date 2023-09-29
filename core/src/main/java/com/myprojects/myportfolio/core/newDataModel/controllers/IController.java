package com.myprojects.myportfolio.core.newDataModel.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.myprojects.myportfolio.clients.general.messages.Message;
import com.myprojects.myportfolio.clients.general.messages.MessageResource;
import com.myprojects.myportfolio.clients.general.messages.MessageResources;
import com.myprojects.myportfolio.clients.general.specifications.SpecificationsBuilder;
import com.myprojects.myportfolio.clients.general.views.IView;
import com.myprojects.myportfolio.clients.general.views.Normal;
import com.sun.istack.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
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

@Slf4j
@SuppressWarnings({"unused", "unchecked"})
public abstract class IController<R> {

    /* CONSTANTS */
    public final String FILTERS = "filters";
    public final String filterKey = "(\\w.+?)";
    public final String filterOperation = "([:!<>])";
    public final String filterValue = "(.+)";
    public final String filtersSeparator = ",";


    /* METHODS */
    abstract ResponseEntity<MessageResources<R>> find(String filters, IView view, Pageable pageable) throws Exception;

    abstract ResponseEntity<MessageResource<R>> get(Integer id, IView view) throws Exception;

    abstract ResponseEntity<MessageResource<R>> create(R resource) throws Exception;

    abstract ResponseEntity<MessageResource<R>> update(Integer id, R resource) throws Exception;

    abstract ResponseEntity<MessageResource<R>> delete(Integer id) throws Exception;


    /* FILTERS AND VIEW */
    public <T> Specification<T> defineFilters(String filters) {
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
                        log.warn("Value is not a date: " + value);
                    }

                    builder.with(key, operation, valueObj);
                }
            }
        }

        return builder.build();
    }

    /**
     * Creates a Specification for a given key and value with the equals operation
     *
     * @param key   the key
     * @param value the value
     * @return the specification
     */
    public <T> Specification<T> findByEquals(String key, Object value) {
        SpecificationsBuilder<T> builder = new SpecificationsBuilder<>();
        builder.with(key, ":", value);
        return builder.build();
    }


    /* RESPONSE */
    /* Build Success Response for single Entity */
    public ResponseEntity<MessageResource<R>> buildSuccessResponse(R element) {
        return this.buildSuccessResponse(element, Normal.value);
    }

    public ResponseEntity<MessageResource<R>> buildSuccessResponse(R element, IView view) {
        return this.buildSuccessResponse(element, view, new ArrayList<>());
    }

    public ResponseEntity<MessageResource<R>> buildSuccessResponse(@NotNull R element, IView view, List<Message> messages) {
        MessageResource<R> result = new MessageResource<>(serializeElement(element, view), messages);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /* RESPONSES */
    /* Build Success Response for Iterables of Entities */
    public ResponseEntity<MessageResources<R>> buildSuccessResponses(Iterable<R> slice) {
        return this.buildSuccessResponses(slice, Normal.value);
    }

    public ResponseEntity<MessageResources<R>> buildSuccessResponses(Iterable<R> slice, IView view) {
        return this.buildSuccessResponses(slice, view, new ArrayList<>());
    }

    public ResponseEntity<MessageResources<R>> buildSuccessResponses(@NotNull Iterable<R> iterable, IView view, List<Message> messages) {
        boolean isLast = true;
        if (iterable instanceof Slice) {
            isLast = ((Slice<R>) iterable).isLast();
        }

        int count = 0;
        List<R> content = new ArrayList<>();
        for (R r : iterable) {
            count++;
            content.add(serializeElement(r, view));
        }

        HttpHeaders headers = new HttpHeaders();
        headers.put("IS-EMPTY", List.of("" + !iterable.iterator().hasNext()));
        headers.put("IS-LAST", List.of("" + isLast));
        headers.put("NUMBER", List.of("" + count));

        MessageResources<R> result = new MessageResources<>(content, messages);
        return new ResponseEntity<>(result, headers, HttpStatus.OK);
    }

    public String fieldMissing(String field) {
        return "Mandatory parameter is missing: " + field;
    }

    public String resourceMissing() {
        return "No valid resource was provided.";
    }

    public String noEntityFound(Integer id) {
        return "No valid entity found with id: " + id;
    }

    /**
     * Create a new ObjectMapper with the given view
     *
     * @return the new ObjectMapper
     */
    private ObjectMapper createObjectMapper() {
        JsonMapper jsonMapper = JsonMapper.builder().build();
        jsonMapper.registerModule(new JavaTimeModule());

        return new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .setConfig(jsonMapper.getSerializationConfig())
                .setConfig(jsonMapper.getDeserializationConfig());
    }

    /**
     * Serialize a complete DTO to a new DTO with the given view
     *
     * @param element the element to serialize
     * @param view    the view to use
     * @return the serialized element
     */
    private R serializeElement(R element, IView view) {

        Type[] actualTypeArguments = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments();
        Class<R> clazz = (Class<R>) actualTypeArguments[actualTypeArguments.length - 1];

        R newElement = null;
        try {
            ObjectMapper mapper = createObjectMapper();
            if (view == null) view = Normal.value;
            String json = mapper.writerWithView(view.getClass()).writeValueAsString(element);
            newElement = mapper.readValue(json, clazz);
        } catch (Exception e) {
            log.error("Error in serializeElement: " + e.getMessage(), e);
        }
        return newElement;
    }

}
