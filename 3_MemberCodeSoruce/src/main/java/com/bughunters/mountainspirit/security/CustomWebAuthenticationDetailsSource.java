package com.bughunters.mountainspirit.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class CustomWebAuthenticationDetailsSource
        extends org.springframework.security.web.authentication.WebAuthenticationDetailsSource {
    @Override
    public CustomWebAuthenticationDetails buildDetails(HttpServletRequest request) {
        return new CustomWebAuthenticationDetails(request);
    }
}
