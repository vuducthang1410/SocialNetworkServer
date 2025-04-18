package wint.webchat.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wint.webchat.entities.conversation.Conversation;
import wint.webchat.modelDTO.reponse.ApiResponse;
import wint.webchat.modelDTO.reponse.ConversationMessageDTO;
import wint.webchat.repositories.IConversationRepository;
import wint.webchat.service.IConversationService;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConversationServiceImpl implements IConversationService{
    private final IConversationRepository iConversationRepository;
    @Override
    @Transactional(readOnly = true)
    public ApiResponse<List<ConversationMessageDTO>> getListConversation(String id, int startGetter, int amountGet) {
        List<ConversationMessageDTO> result=iConversationRepository.getListConversation(id,startGetter,amountGet);
        if(result.isEmpty()){
            return ApiResponse.<List<ConversationMessageDTO>>builder()
                    .code(HttpStatus.OK.value())
                    .error(Collections.emptyMap())
                    .build();
        }
        return ApiResponse.<List<ConversationMessageDTO>>builder()
                .code(HttpStatus.OK.value())
                .error(Collections.emptyMap())
                .data(result)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public Conversation getConversationById(String id) {
        return iConversationRepository.getConversationById(id);
    }

    @Override
    public ResponseEntity<String> add(Conversation conversation) {
       return iConversationRepository.add(conversation);
    }
}
