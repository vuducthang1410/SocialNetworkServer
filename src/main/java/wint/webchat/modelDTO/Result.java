package wint.webchat.modelDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import wint.webchat.common.ResponseCode;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    @JsonProperty("responseCode")
    private String code;
    @JsonProperty("isOk")
    private Boolean isOk;
    @JsonProperty("responseMessage")
    private String responseMessage;

    public static Result Ok() {
        return new Result("00", true, "successful");
    }

    public static Result errorFromCode(ResponseCode responseCode) {
        return new Result(responseCode.getCode(), false, responseCode.getMessage());
    }

    public static Result SYSTEM_ERR() {
        return new Result("ERR_001_0001", false, "System error!");
    }
}
