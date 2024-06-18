package wint.webchat.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChannelRedis {

    AUTH_CHANNEL("AUTH_CHANNEL"),
    MAIL_CHANNEL("MAIL_CHANNEL");
    private final String channelValue;
}
