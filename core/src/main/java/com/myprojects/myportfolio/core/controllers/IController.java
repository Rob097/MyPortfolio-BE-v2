package com.myprojects.myportfolio.core.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.myprojects.myportfolio.clients.general.messages.Message;
import com.myprojects.myportfolio.clients.general.messages.MessageResource;
import com.myprojects.myportfolio.clients.general.messages.MessageResources;
import com.myprojects.myportfolio.clients.general.specifications.SpecificationsBuilder;
import com.myprojects.myportfolio.clients.general.views.IView;
import com.myprojects.myportfolio.clients.general.views.Normal;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@SuppressWarnings({"unused", "unchecked"})
public abstract class IController<R> {

    /* CONSTANTS */
    public final String FILTERS = "filters";
    public final String filterKey = "(\\w.+?)";
    public final String filterOperation = "([:!<>])";
    public final String filterValue = "(.+)";
    public final String filtersSeparator = ",";
    public final String DATE_FORMAT = "dd/MM/yyyy";
    public final String TIME_FORMAT = "HH:mm:ss";
    private static final int MAX_DEPTH = 200;

    private static final Set<Class<?>> SIMPLE_TYPES = new HashSet<>(Arrays.asList(
            String.class, Integer.class, Long.class, Double.class, Float.class, Boolean.class, Character.class, Byte.class, Short.class
    ));


    /* METHODS */
    protected abstract ResponseEntity<MessageResources<R>> find(String filters, IView view, Pageable pageable) throws Exception;

    protected abstract ResponseEntity<MessageResource<R>> get(Integer id, IView view) throws Exception;

    protected abstract ResponseEntity<MessageResource<R>> create(R resource) throws Exception;

    protected abstract ResponseEntity<MessageResource<R>> update(Integer id, R resource) throws Exception;

    protected abstract ResponseEntity<MessageResource<R>> delete(Integer id) throws Exception;


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
     * @return the specifications
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
        return this.buildSuccessResponseOfGenericType(element, view, messages, true);
    }

    public <C> ResponseEntity<MessageResource<C>> buildSuccessResponseOfGenericType(@NotNull C element, IView view, List<Message> messages, boolean logErrors) {
        MessageResource<C> result = new MessageResource<>(serializeElement(element, view, logErrors), messages);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /* RESPONSES */
    /* Build Success Response for Iterables of Entities */
    public ResponseEntity<MessageResources<R>> buildSuccessResponses(Iterable<R> iterable) {
        return this.buildSuccessResponses(iterable, Normal.value);
    }

    public ResponseEntity<MessageResources<R>> buildSuccessResponses(Iterable<R> iterable, IView view) {
        return this.buildSuccessResponses(iterable, view, new ArrayList<>());
    }

    public ResponseEntity<MessageResources<R>> buildSuccessResponses(@NotNull Iterable<R> iterable, IView view, List<Message> messages) {
        return this.buildSuccessResponsesOfGenericType(iterable, view, messages, true);
    }

    public <C> ResponseEntity<MessageResources<C>> buildSuccessResponsesOfGenericType(@NotNull Iterable<C> iterable, IView view, List<Message> messages, boolean logErrors) {
        List<C> content = new ArrayList<>();
        for (C r : iterable) {
            C serializedElement = serializeElement(r, view, logErrors);
            if (serializedElement != null) {
                content.add(serializedElement);
            }
        }

        boolean isLast = true;
        long count = content.spliterator().getExactSizeIfKnown();
        if (iterable instanceof Page<C> page) {
            isLast = page.isLast();
            count = page.getTotalElements() - (count - content.size());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.put("IS-EMPTY", List.of("" + !iterable.iterator().hasNext()));
        headers.put("IS-LAST", List.of("" + isLast));
        // Set the "NUMBER" header with the total count of elements
        headers.put("NUMBER", List.of("" + count));

        MessageResources<C> result = new MessageResources<>(content, messages);
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
    private <C> C serializeElement(C element, IView view, boolean logErrors) {
        try {
            element = removeEmptyObjects(element, 0);
            if (element == null) {
                return null;
            }

            Type[] actualTypeArguments = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments();
            Class<C> clazz = (Class<C>) actualTypeArguments[actualTypeArguments.length - 1];

            ObjectMapper mapper = createObjectMapper();
            if (view == null) view = Normal.value;
            String json = mapper.writerWithView(view.getClass()).writeValueAsString(element);
            return mapper.readValue(json, clazz);
        } catch (Exception e) {
            if (logErrors)
                log.error("Error in serializeElement: " + e.getMessage());
            return element;
        }
    }

    /**
     * Remove empty objects from a given object
     *
     * @param obj   the object to remove empty objects from
     * @param depth the current depth
     * @return the object without empty objects or null if the object is empty
     */
    private <C> C removeEmptyObjects(C obj, int depth) {
        if (obj == null || depth > MAX_DEPTH) {
            return null;
        }

        // Return false if obj is a primitive type or a "simple" type
        if (obj.getClass().isPrimitive() || SIMPLE_TYPES.contains(obj.getClass())) {
            return obj;
        }

        boolean hasNotEmptyFields = false;
        for (Field field : obj.getClass().getDeclaredFields()) {
            if (field.getName().equals("serialVersionUID")) {
                continue; // Skip serialVersionUID field
            }

            field.setAccessible(true);
            try {
                if (field.get(obj) != null) {
                    if (field.get(obj) instanceof Iterable<?> iterable) {
                        Iterator<?> iterator = iterable.iterator();
                        while (iterator.hasNext()) {
                            Object o = iterator.next();
                            Object removed = removeEmptyObjects(o, depth + 1);
                            if (removed == null) {
                                iterator.remove();
                            } else {
                                hasNotEmptyFields = true;
                            }
                        }
                    } else {
                        hasNotEmptyFields = true;
                        continue;
                    }
                }
            } catch (IllegalAccessException e) {
                log.error("Error in isEmptyObject: " + e.getMessage(), e);
            }
        }

        return hasNotEmptyFields ? obj : null;
    }

}
