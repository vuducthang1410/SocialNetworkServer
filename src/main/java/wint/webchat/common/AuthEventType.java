package wint.webchat.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum AuthEventType {
    SAVE_TOKEN_LOGIN("SAVE_TOKEN"),
    REFRESH_ACCESS_TOKEN("REFRESH_TOKEN");
    private String getAuthEventType;
}
