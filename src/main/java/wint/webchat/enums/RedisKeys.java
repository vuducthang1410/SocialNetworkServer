package wint.webchat.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RedisKeys {
    TOKEN("TOKEN"),
    ACCESS_TOKEN("ACCESS_TOKEN"),
    REFRESH_TOKEN("REFRESH_TOKEN"),
    EMAIL_TOKEN("EMAIL_TOKEN"),
    TIME_EXPIRATION_MS("TIME_EXPIRATION_MS");
    private final String valueRedisKey;
}
