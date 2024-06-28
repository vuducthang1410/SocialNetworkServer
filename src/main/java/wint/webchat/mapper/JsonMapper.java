package wint.webchat.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class JsonMapper {
    private final ObjectMapper objectMapper;

    public String objectToJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
    public<T> String objectToJsonT(T t) throws JsonProcessingException {
        return objectMapper.writeValueAsString(t);
    }
    public <T> T jsonToObject(String json, Class<T> clazz) throws JsonProcessingException {
        return objectMapper.readValue(json, clazz);
    }

    public <T, Y> T jsonToObjectGeneric(byte[] data, Class<T> classObject, Class<Y> classGeneric) throws JsonProcessingException, UnsupportedEncodingException {
        String json = new String(data, StandardCharsets.UTF_8);
        json = json.substring(1, json.length() - 1).replaceAll("\\\\\"", "\"");
        TypeReference<T> typeReference = new TypeReference<T>() {
            @Override
            public Type getType() {
                return objectMapper.getTypeFactory().constructParametricType(classObject, classGeneric);
            }
        };
        return objectMapper.readValue(json, typeReference);

    }

    public <T> T jsonPubToObject(byte[] data, Class<T> classObject) throws IOException {
        String json = new String(data, StandardCharsets.UTF_8);
        json = json.substring(1, json.length() - 1).replaceAll("\\\\\"", "\"");
        return objectMapper.readValue(json, classObject);
    }

    public <T> T jsonReidisToObject(String data, Class<T> classObject) throws JsonProcessingException, UnsupportedEncodingException {
//        String json=data.substring(1, data.length() - 1).replaceAll("\\\\\"", "\"");
        return objectMapper.readValue(data, classObject);

    }
}
