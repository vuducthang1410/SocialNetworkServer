package wint.webchat.modelDTO.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NonNull;

@Data
public class FriendRequestDTO {
    @NotNull(message = "id is not null")
    private Long id;
    private int start;
    @Size(max = 30,min = 5,message = "amount must 5< and <30")
    private int amount;
}
