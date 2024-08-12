package wint.webchat.repositories;

import wint.webchat.entities.conversation.Message;
import wint.webchat.modelDTO.reponse.MessageDTO;

import java.util.List;

public interface IMessageRepository extends BaseMethod<Message> {
 List<MessageDTO> getListMessageByConversationId(int amount, Long conversationId, int start);
}
