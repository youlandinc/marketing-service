package com.youland.marketing.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import lombok.NonNull;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author chenning
 */
public class JsonUtil {
    private static final ObjectMapper _mapper;
    static {
        _mapper = createMapperDefault();
    }

    /**
     * ltang: Need this for Instant (https://github.com/FasterXML/jackson-modules-java8)
     * @return
     */
    private static SimpleModule createTimeModule() {
        //return new JavaTimeModule();
        var timeModule = new SimpleModule();
        timeModule.addDeserializer(Instant.class, InstantDeserializer.INSTANT);
        timeModule.addDeserializer(LocalDate.class, LocalDateDeserializer.INSTANCE);
        timeModule.addDeserializer(LocalTime.class, LocalTimeDeserializer.INSTANCE);
        timeModule.addDeserializer(LocalDateTime.class, LocalDateTimeDeserializer.INSTANCE);

        //ltang: To be compatible with @JsonSetter
        timeModule.addSerializer(Instant.class, new ToStringSerializer());
        timeModule.addSerializer(LocalDate.class, new ToStringSerializer());
        timeModule.addSerializer(LocalTime.class, new ToStringSerializer());
        timeModule.addSerializer(LocalDateTime.class, new ToStringSerializer());

        return timeModule;
    }

    private static ObjectMapper createMapperDefault() {
        ObjectMapper mapper
                = new ObjectMapper()
                .enable(SerializationFeature.INDENT_OUTPUT)
                .registerModule(createTimeModule())
                //ltang: Forgiving on unknown fields when deserializing
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

    public static ObjectMapper getObjectMapper() {
        return _mapper;
    }

    public static String stringify(Object obj) {
        if (obj == null)
            return null;
        String json = null;
        try {
            json = _mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static JsonNode toJsonNode(@NonNull Object value) {
        return _mapper.convertValue(value, JsonNode.class);
    }

    public static ObjectNode toObjectNode(@NonNull Object value) {
        return _mapper.convertValue(value, ObjectNode.class);
    }

    public static <T> T fromJsonNode(@NonNull JsonNode value, Class<T> classOfT) {
        return _mapper.convertValue(value, classOfT);
    }

    public static Object string2Obj(@NonNull String jsonText) throws IOException {
        return string2Obj(jsonText, Object.class);
    }

    public static <T> T string2Obj(@NonNull String jsonText, Class<T> classOfT) throws IOException {
        return _mapper.readValue(jsonText, classOfT);
    }

    public static Object file2Obj(@NonNull String filename) throws IOException {
        return file2Obj(filename, Object.class, _mapper);
    }

    public static <T> T file2Obj(@NonNull String filename, Class<T> classOfT) throws IOException {
        return file2Obj(filename, classOfT, _mapper);
    }

    public static <T> T file2Obj(@NonNull String filename
            , Class<T> classOfT
            , ObjectMapper mapper) throws IOException {
        if (mapper == null)
            mapper = _mapper;
        try (Reader reader = Files.newBufferedReader(Paths.get(filename))){
            return mapper.readValue(reader, classOfT);
        }
    }

    public static <T> T deepClone(@NonNull T origin) throws JsonProcessingException {
        return deepClone(origin, _mapper);
    }

    public static <T> T deepClone(@NonNull T origin, ObjectMapper mapper) throws JsonProcessingException {
        if (mapper == null)
            mapper = _mapper;

        @SuppressWarnings("unchecked")
        T deepCopy = (T) mapper
                .readValue(mapper.writeValueAsString(origin), origin.getClass());
        return deepCopy;
    }

    /**
     * NOTES: ltang - Merging Array will always insert new values. If you want to update collection,
     * better merging Map rather than Array.
     * Merge other into target and return merged node. B/c in place,
     * the target and merged is the same reference.
     * @param target
     * @param other
     * @return
     * @throws IOException
     */
    public static ObjectNode mergeNodeInPlace(@NonNull final ObjectNode target
            , @NonNull final ObjectNode other) throws IOException {
        ObjectReader updater = _mapper.readerForUpdating(target);
        var merged = (ObjectNode) updater.readValue(other);
        return merged;
    }

    public static void injectFieldIfMissing(@NonNull ObjectNode head
            , @NonNull String fieldName
            , @NonNull String fieldValue) {
        JsonNode node = head.get(fieldName);
        if (node == null) {
            head.put(fieldName, fieldValue);
        }
    }

    public static String yamlDashPathToJsonCamelCase(String yamlPath) {
        if (yamlPath == null)
            return null;

        StringBuilder jsonPath
                = new StringBuilder(yamlPath.replace(".", "/"));

        int index = 0;
        while (index >= 0) {
            index = jsonPath.indexOf("-");
            if (index >= 0) {
                jsonPath.deleteCharAt(index);
                if (index < jsonPath.length()) {
                    char camelCase = Character.toUpperCase(jsonPath.charAt(index));
                    jsonPath.deleteCharAt(index);
                    jsonPath.insert(index, camelCase);
                }
            }
        }

        if (jsonPath.charAt(0) != '/')
            jsonPath = jsonPath.insert(0, '/');

        return jsonPath.toString();
    }
}
