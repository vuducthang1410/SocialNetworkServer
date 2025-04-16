package wint.webchat.modelDTO.request;

import lombok.Data;

@Data
public class FriendIdDTO {
    private String senderId;
    private String receiverId;
}
