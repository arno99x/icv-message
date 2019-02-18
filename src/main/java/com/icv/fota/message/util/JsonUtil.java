package com.icv.fota.message.util;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by lirichen on 2017/7/3.
 */
public class JsonUtil {
    private static final Logger log = LoggerFactory.getLogger(JsonUtil.class);

    private JsonUtil(){
        throw new IllegalStateException("Utility class");
    }

    public static <T> String objectToString(T t) {
        String jsonResult = "";
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            jsonResult = ow.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        return jsonResult;
    }

    public static <T extends Object> T stringToObject(String jsonString, Class<T> t) {
        T result = null;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            result = objectMapper.readValue(jsonString, t);

        } catch (JsonGenerationException |JsonMappingException e) {
            log.error(e.getMessage());
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
        return result;
    }

}
