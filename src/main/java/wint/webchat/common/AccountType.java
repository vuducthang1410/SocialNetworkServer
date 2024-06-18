package wint.webchat.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AccountType {
    SYSTEM("SYSTEM"),
    GOOGLE("GOOGLE");
    private String accountType;
}
