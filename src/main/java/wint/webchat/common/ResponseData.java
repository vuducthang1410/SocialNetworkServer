package wint.webchat.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import wint.webchat.modelDTO.Result;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseData<T> {
    private String transactionId;
    private T result;
    private T data;

    public static <T> ResponseData<T> createResponse(Map<String,Object>data) {
        ResponseData responseData = new ResponseData<>();
        responseData.setTransactionId(Constant.TRANSACTION_ID_KEY);
        responseData.setResult(data.getOrDefault(Constant.RESPONSE_KEY.RESULT, Result.SYSTEM_ERR()));
        responseData.setData(data.getOrDefault(Constant.RESPONSE_KEY.DATA,"successful"));
        return responseData;
    }
}
