package wint.webchat.event.mailEvent;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;
import wint.webchat.mapper.JsonMapper;

@Component
@RequiredArgsConstructor
public class MailListener implements MessageListener {
    private final JsonMapper jsonMapper;
//    private final MailSubscriber mailSubscriber;
    @Override
    public void onMessage(Message message, byte[] pattern) {
//        try {
//            PubSubMessage<MailResetPasswordDTO> pubSubMessage=jsonMapper.jsonPubToObject(message.getBody(), PubSubMessage.class, MailResetPasswordDTO.class);
//            if(pubSubMessage.getEvenType().equals(MailEventType.SEND_MAIL_RESET_PASSWORD.getGetMailEventType())){
////                mailSubscriber.
//            }
//        } catch (JsonProcessingException | UnsupportedEncodingException e) {
//            throw new RuntimeException(e);
//        }
    }
}
