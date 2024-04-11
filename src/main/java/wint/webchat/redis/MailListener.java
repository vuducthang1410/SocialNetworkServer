package wint.webchat.redis;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class MailListener implements MessageListener {
    @Override
    public void onMessage(Message message, byte[] pattern) {

    }
}
