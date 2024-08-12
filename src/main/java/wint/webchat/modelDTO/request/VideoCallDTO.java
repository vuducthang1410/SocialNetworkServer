package wint.webchat.modelDTO.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class VideoCallDTO {
    private final Long senderId;
    private final Long receiverid;
}
