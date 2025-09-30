//package com.bughunters.mountainspirit.common;
//
//import io.jsonwebtoken.*;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.core.env.Environment;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//@Component
//@Slf4j
//public class JwtFilter extends OncePerRequestFilter {
//    private Environment env;
//
//    public JwtFilter(Environment env) {
//        this.env = env;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        // 헤더에 Authorization key값에 들어있는 value 추출
//        String authInfo = request.getHeader("Authorization");
//
//        // Authorization 검사
//        if (authInfo == null || !authInfo.startsWith("Bearer ")) {
//            throw new ServletException("잘못된 권한");
//        }
//        log.info("Authorization으로 넘어온 값 : {}", authInfo);
//
//        // claim 추출
//        String jwtValue = authInfo.substring(7);
//        Claims claimData = null;
//        try {
//            claimData = Jwts.parser()
//                    .setSigningKey(env.getProperty("token.secret"))
//                    .parseClaimsJws(jwtValue)
//                    .getBody();
//        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
//            log.info("유효하지 않은 JWT Token(아무 값이 없음)");
//            return;
//        } catch (ExpiredJwtException e) {
//            log.info("만료 기간이 지남");
//            return;
//        } catch (UnsupportedJwtException e) {
//            log.info("지원하지 않는 JWT Token(지원되지 않는 양식)");
//            return;
//        } catch (IllegalArgumentException e) {
//            log.info("토큰의 클레임이 비어있음");
//            return;
//        }
//        log.info("JWT sub 파싱값 : {}", claimData);
//
//        // claim 정보 request body에 적재
//        String username = (String)claimData.get("username");
//        long id = Long.parseLong(claimData.get("id").toString());
//        UserInfo userInfo = new UserInfo();
//        userInfo.setUsername(username);
//        userInfo.setId(id);
////        request.setAttribute("username", claimData.get("username"));
////        request.setAttribute("userid", claimData.get("id"));
////        log.info("username 값 : {}",claimData.get("username").toString());
////        log.info("id 값 : {}",claimData.get("id").toString());
//        request.setAttribute("userInfo", userInfo);
//        log.info("userInfo 값 : {}", userInfo);
//
//        filterChain.doFilter(request, response);
//    }
//}
//
