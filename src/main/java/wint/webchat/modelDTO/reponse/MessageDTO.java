package wint.webchat.modelDTO.reponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageDTO {
    private Long idMessage;
    private Long idSender;
    private Timestamp timeSend;
    private String typeMessage;
    private String content;
    private String url;
    private String typeTime;
    private Double timeCall;
    private Long conversationId;
}
