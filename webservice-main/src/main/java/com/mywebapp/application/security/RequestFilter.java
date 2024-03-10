package com.mywebapp.application.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter to intercept incoming requests and handle authentication.
 */
@Component
public class RequestFilter extends OncePerRequestFilter {

    @Autowired
    MyUserDetailsService userDetailsService;

    @Autowired
    TokenUtils tokenUtils;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = httpServletRequest.getHeader("Authorization");
        String username = null;
        String pwd = null;

        if( authorizationHeader != null && authorizationHeader.startsWith("Basic ") ) {
           username = tokenUtils.extractUserName(authorizationHeader);
           pwd = tokenUtils.extractPassword(authorizationHeader);
        }
        if(username != null && pwd != null){
            boolean authenticated = userDetailsService.validateUser(username,pwd);
            if(authenticated){
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,null,userDetails.getAuthorities()
                );
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }else {
                httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            }

        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);

    }

}
