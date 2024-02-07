package wint.webchat.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Emoji {
    LIKE(1),
    LOVE(2),
    DISLIKE(3),
    FUNNY(5),
    ANGRY(4),
    SAD(6);
    private final int emojiValue;

}
