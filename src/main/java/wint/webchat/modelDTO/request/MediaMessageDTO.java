package wint.webchat.modelDTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
@Data
@AllArgsConstructor
public class MediaMessageDTO {
    private MultipartFile mediaFile;
    private String typeMessage;
    private Long conversationId;
    private Long senderId;
    private Long memberReceiverId;
}
