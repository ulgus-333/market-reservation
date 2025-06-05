package com.reservation.authentication.config.filter;

import com.auth0.jwt.JWT;
import com.reservation.authentication.domain.principal.RequestUser;
import com.reservation.authentication.domain.principal.impl.PrincipalContent;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (token.startsWith("Bearer ")) {
            PrincipalContent principal = PrincipalContent.from(JWT.decode(token.substring(7)));

            if (SecurityContextHolder.getContext().getAuthentication() == null && principal.valid()) {
                RequestUser requestUser = RequestUser.from(principal);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(requestUser, null, requestUser.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}
