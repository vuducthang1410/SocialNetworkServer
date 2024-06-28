package wint.webchat.modelDTO.request;

import lombok.Data;

@Data
public class FriendIdDTO {
    private Long senderId;
    private Long receiverId;
}
