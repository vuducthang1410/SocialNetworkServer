package wint.webchat.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Media {
    VIDEO(1),
    IMAGE(2);
    private int valueMedia;
}
