package org.example.front.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;

@UtilityClass
public class ExtractMessageUtil {

    private ObjectMapper objectMapper = new ObjectMapper();

    public String extractErrorMessage(String errorContent) {
        Map<String, Object> errorResponse = new HashMap<>();
        try {
            errorResponse = objectMapper.readValue(errorContent, Map.class);
        } catch (JsonProcessingException e) {
            return format("Ошибка парсинга JSON: %s", e.getMessage());
        }
        if (errorResponse.containsKey("message")) {
            return (String) errorResponse.get("message");
        } else if (errorResponse.containsKey("error")) {
            return (String) errorResponse.get("error");
        } else {
            return "Неизвестная ошибка";
        }
    }
}
