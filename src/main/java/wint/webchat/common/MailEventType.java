package wint.webchat.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum MailEventType {
    SEND_MAIL_RESET_PASSWORD("RESET");
    private String getMailEventType;
}
