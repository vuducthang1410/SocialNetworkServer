package wint.webchat.event.authEvent;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;
import wint.webchat.common.AuthEventType;
import wint.webchat.mapper.JsonMapper;
import wint.webchat.modelDTO.request.AuthLoginDTO;
import wint.webchat.modelDTO.PubSubDTO.PubSubMessage;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

@Component
@RequiredArgsConstructor
public class AuthListener implements MessageListener {
    private final AuthSubscriber authSubscriber;
    private final JsonMapper jsonMapper;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String data=new String(message.getBody(), StandardCharsets.UTF_8);
        System.out.println(data);
        try {
            var dataObject=jsonMapper.jsonPubToObject(message.getBody(),PubSubMessage.class);
            if(dataObject.getEvenType().equalsIgnoreCase(AuthEventType.SAVE_TOKEN_LOGIN.getGetAuthEventType())){
                authSubscriber.saveTokenToServer((LinkedHashMap<String, Object>) dataObject.getPayload());
            }else if(dataObject.getEvenType().equalsIgnoreCase(AuthEventType.REFRESH_ACCESS_TOKEN.getGetAuthEventType())){
                authSubscriber.saveNewAccessTokenToServer((LinkedHashMap<String, Object>) dataObject.getPayload());
            }
            System.out.println(dataObject);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
