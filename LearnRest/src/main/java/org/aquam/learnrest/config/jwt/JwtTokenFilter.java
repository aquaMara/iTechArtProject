package org.aquam.learnrest.config.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

// фильтрует запросы на наличие токена
@RequiredArgsConstructor
public class JwtTokenFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    // token from request
    // jwtTokenProvider.validateToken(token) - токен валиден
    // каждый запрос проверяем на токен
    // есть токен = можно продолжатьб нет токена = нет и аутентификации, до свидания
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) servletRequest);
        if (((HttpServletRequest) servletRequest).getServletPath().equals("/learn/login")
            || ((HttpServletRequest) servletRequest).getServletPath().equals("/learn/register")) {
            filterChain.doFilter(servletRequest, servletResponse);
        }
        if (token != null && jwtTokenProvider.validateToken(token)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            if (authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(servletRequest, servletResponse);

        }
    }
}
