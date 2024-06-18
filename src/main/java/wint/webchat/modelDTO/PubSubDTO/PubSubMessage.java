package wint.webchat.modelDTO.PubSubDTO;

import lombok.*;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PubSubMessage <T>{
    private String messageId;
    private Long timeMessageCreate;
    private T payload;
    private String evenType;
    private Map<String, String> attributes;
}
