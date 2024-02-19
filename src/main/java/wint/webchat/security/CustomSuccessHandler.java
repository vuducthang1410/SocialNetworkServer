package wint.webchat.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.stream.Collectors;
@Configuration
public class CustomSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        var authorities=authentication.getAuthorities();
        var roles=authorities.stream().map(GrantedAuthority::getAuthority).findFirst();
        if(roles.orElse("").equals("admin")) {
            response.sendRedirect("/admin/home");
        }else if(roles.orElse("").equals("user")){
            response.sendRedirect("home");
        }else {
            response.sendRedirect("/error");
        }
    }
}
