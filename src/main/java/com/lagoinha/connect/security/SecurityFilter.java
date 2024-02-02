package com.lagoinha.connect.security;

import com.lagoinha.connect.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    TokenService tokenService;
    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);
        if(token == null){
            if(request.getCookies() != null){
                for (Cookie c : request.getCookies()){
                    if(c.getName().equals("token")){
                        token = c.getValue();
                    }
                }
            }
        }
        if(token != null){
            var login = tokenService.validateToken(token);
            UserDetails user = userRepository.findByLogin(login);
            if(user != null){
                var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else{
                if(request.getCookies() != null){
                    for (Cookie cookie : request.getCookies()) {
                        if (cookie.getName().equals("token")) {
                            cookie.setMaxAge(0);
                        }
                    }
                }
            }
        }else{
            String encodedRedirectURL = ((HttpServletResponse) response).encodeRedirectURL(
                    request.getContextPath() + "/login");
            response.setStatus(HttpStatus.OK.value());
            response.setHeader("Location", encodedRedirectURL);
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }


}
