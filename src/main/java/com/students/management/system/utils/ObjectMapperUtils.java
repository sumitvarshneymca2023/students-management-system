package com.students.management.system.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

@Slf4j
public class ObjectMapperUtils {

    private static final ModelMapper modelMapper;

    private static final ObjectMapper objectMapper;

    static {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        objectMapper = new ObjectMapper();
        objectMapper.disable(FAIL_ON_UNKNOWN_PROPERTIES)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

    }

    private ObjectMapperUtils() {
    }

    public static <D, T> D map(final T entity, Class<D> outClass) {
        return modelMapper.map(entity, outClass);
    }

    public static <D, T> List<D> mapAll(final Collection<T> entityList, Class<D> outCLass) {
        return entityList.stream()
                .map(entity -> map(entity, outCLass))
                .collect(Collectors.toList());
    }

    public static <D, T> Set<D> mapAllToSet(final Collection<T> entityList, Class<D> outCLass) {
        return entityList.stream()
                .map(entity -> map(entity, outCLass))
                .collect(Collectors.toSet());
    }

    public static <S, D> D map(final S source, D destination) {
        modelMapper.map(source, destination);
        return destination;
    }

    public static <T extends Object> T jsonToObjectMapper(String data, Class<T> className) {
        T t = null;
        try {
            t = objectMapper.readValue(data, className);
        } catch (Exception e) {
            log.error("EXCEPTION WHILE CONVERTING JSON TO OBJECT::DATA:: {}, CLASSNAME:: {}", data, className, e);
        }
        return t;
    }


    /**
     * convert object  to json
     *
     * @param data
     * @return
     */
    public static <T> String objectToJsonMapper(T data) {
        String jsonString = null;
        try {
            jsonString = objectMapper.writeValueAsString(data);
        } catch (Exception e) {
            log.error("ERROR WHILE CONVERTING OBJECT TO JSON::DATA:: {}", data, e);
        }
        return jsonString;
    }

}