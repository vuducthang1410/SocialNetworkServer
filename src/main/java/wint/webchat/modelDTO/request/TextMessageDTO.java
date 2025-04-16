package wint.webchat.modelDTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
@Data
@AllArgsConstructor
public class TextMessageDTO {
    private String content;
    private String typeMessage;
    private String conversationId;
    private String senderId;
    private String memberReceiverId;
}
