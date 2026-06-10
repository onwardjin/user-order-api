package io.github.blairjin.user_order_api.infrastructure.jwt;

import io.github.blairjin.user_order_api.application.auth.service.BlacklistTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final BlacklistTokenService blacklistTokenService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String bearer = request.getHeader("Authorization");

        if(bearer==null || !bearer.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        String token = bearer.substring(7);

        if(!jwtProvider.validateToken(token)){
            filterChain.doFilter(request, response);
            return;
        }

        // Blacklist check not applied.
        // AccessToken expires in 20 minutes.

//        if(blacklistTokenService.exists(token)){
//            filterChain.doFilter(request, response);
//            return;
//        }

        JwtUserInfo userInfo = jwtProvider.getUserInfo(token);

        CustomUserPrincipal principal =
                new CustomUserPrincipal(userInfo.userId(), userInfo.loginId(), userInfo.role());
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}