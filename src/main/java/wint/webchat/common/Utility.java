package wint.webchat.common;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Data
public class Utility {
    public static String getHeaderParam(String headerKey) {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if (attributes == null) return null;
        HttpServletRequest request
                = ((ServletRequestAttributes) attributes).getRequest();
        return request.getHeader(headerKey);
    }
}
