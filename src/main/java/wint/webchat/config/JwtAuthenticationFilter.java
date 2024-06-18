package wint.webchat.config;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import wint.webchat.redis.AuthRedisService;
import wint.webchat.service.Impl.JwtServiceImpl;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtServiceImpl jwtServiceImpl;
    private final AuthRedisService authRedisService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization") != null ? request.getHeader("Authorization") : getTokenFromUri(request);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            String accessToken = authHeader.substring(7);
            String username = jwtServiceImpl.getUsernameFromToken(accessToken);
            if (authRedisService.isAccessTokenContainRedis(accessToken, username))
                authenticateTokenAndSetSecurityContext(accessToken, request, username);
            else{
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write("TOKEN_NOT_VALID");
                return;
            }
        } catch (ExpiredJwtException eje) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("TOKEN_EXPIRATION");
            return;
        }
        filterChain.doFilter(request, response);
    }

    private void authenticateTokenAndSetSecurityContext(String jwt, HttpServletRequest request, String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(username, jwt,
                        jwtServiceImpl.getAuthoritiesFromToken(jwt));
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        context.setAuthentication(authToken);
        SecurityContextHolder.setContext(context);
    }

    private String getTokenFromUri(HttpServletRequest request) {
        String query = request.getQueryString();
        if (query != null) {
            String[] queryParams = query.split("&");
            for (String param : queryParams) {
                String[] pair = param.split("=");
                if (pair.length == 2 && "Authorization".equals(pair[0])) {
                    return pair[1].replace("Bearer%20", "Bearer ");
                }
            }
        }
        return null;
    }
}

