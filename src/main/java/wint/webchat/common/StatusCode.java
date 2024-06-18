package wint.webchat.common;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
public class StatusCode  {
    public static final int SUCCESS_CODE=200;
    public static final int REFRESH_TOKEN_EXPIRATION_CODE=406;
    public static final int ACCESS_TOKEN_EXPIRATION_CODE=407;
    public static final int TOKEN_NOT_VALID_CODE=407;
    public static final int SERVER_ERROR=500;

}
