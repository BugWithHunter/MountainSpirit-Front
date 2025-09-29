package com.bughunters.mountainspirit.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JsonAuthSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest req,
                                        HttpServletResponse res,
                                        Authentication authentication) throws IOException {

        // 기본 응답 값
        int    status  = HttpServletResponse.SC_OK;
        String code    = "OK";
        String message = "로그인에 성공했습니다.";

        // 로그인 사용자 정보
        String username = authentication.getName();
        List<String> authorities = toAuthorityList(authentication.getAuthorities());
        // 필요시 Principal 캐스팅해서 추가 프로필 정보 뽑기
        // var principal = (CustomUserPrincipal) authentication.getPrincipal();

        // 추가 필드 예시(원하면 req에 Attribute로 담아 오거나, DB 조회해서 채우기)
        Map<String, Object> extra = Map.of(
                "loginAt", LocalDateTime.now().toString(),
                "sessionId", req.getSession(false) != null ? req.getSession(false).getId() : ""
        );

        // JSON 응답
        res.setStatus(status);
        res.setContentType("application/json;charset=UTF-8");

        StringBuilder sb = new StringBuilder()
                .append("{\"success\":true")
                .append(",\"code\":\"").append(code).append("\"")
                .append(",\"message\":\"").append(escapeJson(message)).append("\"")
                .append(",\"user\":{")
                .append("\"username\":\"").append(escapeJson(username)).append("\",")
                .append("\"authorities\":").append(toJsonArray(authorities))
                .append("}");

        if (!extra.isEmpty()) {
            sb.append(",\"extra\":{");
            boolean first = true;
            for (var e : extra.entrySet()) {
                if (!first) sb.append(',');
                first = false;
                sb.append('"').append(escapeJson(e.getKey())).append("\":\"")
                        .append(escapeJson(String.valueOf(e.getValue()))).append('"');
            }
            sb.append('}');
        }

        sb.append('}');
        res.getWriter().write(sb.toString());
    }

    private List<String> toAuthorityList(Collection<? extends GrantedAuthority> authorities) {
        return authorities == null ? List.of()
                : authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    }

    private String toJsonArray(List<String> values) {
        if (values == null || values.isEmpty()) return "[]";
        return values.stream()
                .map(v -> "\"" + escapeJson(v) + "\"")
                .collect(Collectors.joining(",", "[", "]"));
    }

    private String escapeJson(String s) {
        return s == null ? "" : s.replace("\\", "\\\\").replace("\"", "\\\"");
    }


}
