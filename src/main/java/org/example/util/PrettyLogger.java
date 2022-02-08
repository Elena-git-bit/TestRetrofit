package org.example.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import okhttp3.internal.platform.Platform;
import okhttp3.logging.HttpLoggingInterceptor;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PrettyLogger implements HttpLoggingInterceptor.Logger {
    ObjectMapper mapper = new ObjectMapper();

    @Override
    public void log(String message) {
        String trimmedMessage = message.trim();
        if ((trimmedMessage.startsWith("{") && trimmedMessage.endsWith("}"))
                || (trimmedMessage.startsWith("[") &&
                trimmedMessage.endsWith("]"))) {
            try {
                Object value = null;
                try {
                    value = mapper.readValue(message, Object.class);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                String prettyJson =
                        mapper.writerWithDefaultPrettyPrinter().writeValueAsString(value);
                Platform.get().log(prettyJson, Platform.INFO, null);
            } catch (JsonProcessingException e) {
                Platform.get().log(message, Platform.WARN, e);
            }
        } else {
            Platform.get().log(message, Platform.INFO, null);
        }
    }
}
