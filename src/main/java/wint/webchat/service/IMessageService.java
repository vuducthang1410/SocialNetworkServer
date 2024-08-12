package wint.webchat.service;

import wint.webchat.modelDTO.reponse.ApiResponse;
import wint.webchat.modelDTO.reponse.MessageDTO;
import wint.webchat.modelDTO.request.MediaMessageDTO;
import wint.webchat.modelDTO.request.TextMessageDTO;

import java.util.List;

public interface IMessageService {
 ApiResponse<List<MessageDTO>> getListMessageByConversationId(int amount,Long conversationId,int start);
 MessageDTO saveMessage(TextMessageDTO textMessageDTO);
 MessageDTO saveMediaMessage(MediaMessageDTO mediaMessageDTO);
}
