package wint.webchat.modelDTO.reponse;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;
@Data
@AllArgsConstructor
public class ConversationMessageDTO {
    private Long userId;
    private String fullName;
    private Boolean isOnline;
    private String urlAvatar;
    private Long conversationId;
    private Long memberConversationId;
    private Long messageId;
    private Long messageMemberConversationId;
    private String typeMessage;
    private Timestamp timeSend;
}
