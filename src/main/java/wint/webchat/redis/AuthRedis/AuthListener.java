package wint.webchat.redis.AuthRedis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthListener implements MessageListener {
    private final AuthProducer authProducer;
    @Override
    public void onMessage(Message message, byte[] pattern) {
        authProducer.saveNewToken(message.toString().substring(1,message.toString().length()-1));
    }
}
