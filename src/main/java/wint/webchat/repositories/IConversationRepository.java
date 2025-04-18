package wint.webchat.repositories;

import wint.webchat.entities.conversation.Conversation;
import wint.webchat.modelDTO.reponse.ConversationMessageDTO;

import java.util.List;

public interface IConversationRepository extends BaseMethod<Conversation> {
List<ConversationMessageDTO> getListConversation(String id, int startGetter, int amountGet);
    Conversation getConversationById(String id);
}
