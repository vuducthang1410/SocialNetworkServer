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
import wint.webchat.redis.AuthRedis.AuthProducer;
import wint.webchat.service.Impl.CustomUserDetailServiceImpl;
import wint.webchat.service.Impl.JwtServiceImpl;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtServiceImpl jwtServiceImpl;
    private final CustomUserDetailServiceImpl userDetailService;
    private final AuthProducer authProducer;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            String jwt = authHeader.substring(7);
            authenticateTokenAndSetSecurityContext(jwt, request);
        } catch (ExpiredJwtException eje) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("TOKEN_EXPIRATION");
            return;
        }
        filterChain.doFilter(request, response);
    }

    private void authenticateTokenAndSetSecurityContext(String jwt, HttpServletRequest request) {
        String username = jwtServiceImpl.getUsernameFromToken(jwt);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(username, jwt,
                        jwtServiceImpl.getAuthoritiesFromToken(jwt));
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        context.setAuthentication(authToken);
        SecurityContextHolder.setContext(context);
    }
}
