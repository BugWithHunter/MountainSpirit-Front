package com.bughunters.mountainspirit.security;

import jakarta.servlet.http.HttpServletRequest;

// Authenticatre Detaile에 추가로 데이터를 담기 위함
public class CustomWebAuthenticationDetails extends org.springframework.security.web.authentication.WebAuthenticationDetails {
    private final String clientIp;
    private final String userAgent;
    private final String deviceId;   // 헤더나 바디에서
    private final String requestId;  // 트레이싱용

    public CustomWebAuthenticationDetails(HttpServletRequest request) {
        super(request); // remoteAddress, sessionId 세팅
        this.clientIp  = resolveClientIp(request);
        this.userAgent = request.getHeader("User-Agent");
        this.deviceId  = request.getHeader("X-Device-Id");
        this.requestId = request.getHeader("X-Request-Id");
    }
    public String getClientIp()  { return clientIp; }
    public String getUserAgent() { return userAgent; }
    public String getDeviceId()  { return deviceId; }
    public String getRequestId() { return requestId; }

    private static String resolveClientIp(HttpServletRequest req) {
        String xff = req.getHeader("X-Forwarded-For");
        if (xff != null && !xff.isBlank()) return xff.split(",")[0].trim();
        String xri = req.getHeader("X-Real-IP");
        return (xri != null && !xri.isBlank()) ? xri : req.getRemoteAddr();
    }
}

