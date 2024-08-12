package wint.webchat.service;

import org.springframework.http.ResponseEntity;
import wint.webchat.entities.conversation.Conversation;
import wint.webchat.modelDTO.reponse.ApiResponse;
import wint.webchat.modelDTO.reponse.ConversationMessageDTO;

import java.util.List;

public interface IConversationService {
  ApiResponse<List<ConversationMessageDTO>> getListConversation(Long id, int startGetter, int amountGet);
Conversation getConversationById(Long id);
  ResponseEntity<String> add(Conversation conversation);
}
