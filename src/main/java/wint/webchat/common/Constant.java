package wint.webchat.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class Constant {
    public static final String data="/";
    public interface RESPONSE_KEY{
        String RESULT = "RESULT_KEY";
        String DATA = "DATA_KEY";
    }
    @Getter
    public enum Gender {
        MALE(1),
        FEMALE(0),
        OTHER(2);
        private final int genderValue;

        Gender(int genderValue) {
            this.genderValue = genderValue;
        }
        public int getGenderValue() {
            return genderValue;
        }
    }
    @AllArgsConstructor
    @Getter
    public enum AccountType {
        SYSTEM("SYSTEM"),
        GOOGLE("GOOGLE");
        private String accountType;
    }
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public enum MailEventType {
        SEND_MAIL_RESET_PASSWORD("RESET");
        private String getMailEventType;
    }
    @Getter
    @AllArgsConstructor
    public enum Media {
        VIDEO(1),
        IMAGE(2);
        private int valueMedia;
    }
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
    @Getter
    public class StatusCode  {
        public static final int SUCCESS_CODE=200;
        public static final int REFRESH_TOKEN_EXPIRATION_CODE=406;
        public static final int ACCESS_TOKEN_EXPIRATION_CODE=407;
        public static final int TOKEN_NOT_VALID_CODE=407;
        public static final int SERVER_ERROR=500;

    }
    @Getter
    @AllArgsConstructor
    public enum ChannelRedis {

        AUTH_CHANNEL("AUTH_CHANNEL"),
        MAIL_CHANNEL("MAIL_CHANNEL");
        private final String channelValue;
    }
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public enum AuthEventType {
        SAVE_TOKEN_LOGIN("SAVE_TOKEN"),
        REFRESH_ACCESS_TOKEN("REFRESH_TOKEN"),
        LOGOUT("LOGOUT"),
        LOGOUT_ALL("LOGOUT_ALL");
        private String getAuthEventType;
    }
    public interface STATUS{
        public static final String YES="Y";
        public static final String NO="N";
    }
}
