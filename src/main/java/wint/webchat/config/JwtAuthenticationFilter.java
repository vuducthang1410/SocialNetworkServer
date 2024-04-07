package wint.webchat.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import wint.webchat.security.CustomUserDetail;
import wint.webchat.service.Impl.CustomUserDetailServiceImpl;
import wint.webchat.service.Impl.JwtServiceImpl;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtServiceImpl jwtServiceImpl;
    private final CustomUserDetailServiceImpl userDetailService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        String jwt;
        final String username;
        if (authHeader == null && SecurityContextHolder.getContext().getAuthentication() == null) {
            filterChain.doFilter(request, response);
            return;
        }
        assert authHeader != null;
        jwt = authHeader.substring(7);
        username = jwtServiceImpl.getUsernameFromToken(jwt);
        CustomUserDetail customUserDetail = (CustomUserDetail) userDetailService.loadUserByUsername(username);
        if (jwtServiceImpl.isTokenValid(jwt, customUserDetail)) {
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    customUserDetail, null, customUserDetail.getAuthorities()
            );
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            context.setAuthentication(authToken);
            SecurityContextHolder.setContext(context);
            filterChain.doFilter(request, response);
        }
    }
}
