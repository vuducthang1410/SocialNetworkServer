package wint.webchat.modelDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberId {
    private Long userIdReceiver;
    private Long idSenderConversation;
}
