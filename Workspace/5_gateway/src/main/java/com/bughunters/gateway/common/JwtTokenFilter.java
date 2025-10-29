package com.bughunters.gateway.common;

import com.bughunters.gateway.DTO.AuthzPropertiesDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
public class JwtTokenFilter extends AbstractGatewayFilterFactory<JwtTokenFilter.Config> {
//    private Environment env;

    @Value(value = "${token.secret}")
    private String tokenSecret;

    private AuthzPropertiesDTO props;
    private final AntPathMatcher matcher = new AntPathMatcher();

    @Autowired
    public JwtTokenFilter(AuthzPropertiesDTO authzPropertiesDTO) {
        super(Config.class);
        this.props = authzPropertiesDTO;
    }

    public static class Config {
    }


    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            // 헤더 추출
            ServerHttpRequest request = exchange.getRequest();
            String path = request.getURI().getPath();
            HttpMethod method = request.getMethod();

            // gateway에서 각 서비스로 서비스 경로(member-clinet, main-client)를 제외하고 보내기위한 설정 때문에
            // 서비스 경로가 들어오지 않아 원래 서비스 경로까지 포함된 경로를 얻기 위함
            Set<URI>  originals = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ORIGINAL_REQUEST_URL_ATTR);
            if (originals != null && !originals.isEmpty()) {
                URI originalUri = originals.iterator().next();
                path = originalUri.getPath();   // 원래 요청 경로
            }

            AuthzPropertiesDTO.Rule rule = matchRule(path, method);
            if (rule == null) {
                return onError(exchange, "No matching rule", HttpStatus.FORBIDDEN);
            }
            // 로그인, 회원가입 토근 없어도 통과
            if (Boolean.TRUE.equals(rule.getPermitAll())) {
                return chain.filter(exchange);
            }

            // 토큰 추출
            String bearerToken = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String jwt = bearerToken.substring(7);
            String subject = null;
            Claims claims = null;
            List<String> auths = null;
            // sub 클레임 추출
            /* 설명. 기본적으로 우리 서버에서 만들었고, 만료기간이 지나지 않았으며,
             *      토큰 안에 'sub'라는 등록된 클레임이 존재하는지 확인
             * */
            try {

                claims = Jwts.parser()
                        .setSigningKey(tokenSecret)
                        .parseClaimsJws(jwt)
                        .getBody();

                auths = (List<String>) claims.get("auth");

                if(rule.getAnyRole() != null){
                    if (!checkAnyRole(rule.getAnyRole(), auths)) {
                        return onError(exchange, "anyRole not satisfied", HttpStatus.FORBIDDEN);
                    }
                } else if(rule.getAllRole() != null){
                    if (!checkAllRole(rule.getAllRole(), auths)) {
                        return onError(exchange, "allRole not satisfied", HttpStatus.FORBIDDEN);
                    }
                }
                subject = claims.getSubject();
            } catch (Exception e) {
                return onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED);
            }
            log.info("sub 값 : {}", subject);

            /* 설명. 토큰의 payload에 subject 클레임 자체가 없거나 내용물이 없는지 확인 */
            if (subject == null || subject.isEmpty()) {
                return onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED);
            }

            if (auths.contains("ROLE_ADMIN")) {
                log.info("ADMIN 계정");
            }


            return chain.filter(exchange);
        });
    }

    private AuthzPropertiesDTO.Rule matchRule(String path, HttpMethod method) {
        if (props.getRules() == null) return null;
        for (AuthzPropertiesDTO.Rule r : props.getRules()) {
            if (matcher.match(r.getPattern(), path)) {
                if (r.getMethods() == null || r.getMethods().isEmpty()
                        || (method != null && r.getMethods().contains(method.name()))) {
                    return r;
                }
            }
        }
        return null;
    }

    private boolean checkAnyRole(List<String> any, List<String> user) {
        if (any == null || any.isEmpty()) return true; // 조건 없음
        return user.stream().anyMatch(any::contains);
    }

    private boolean checkAllRole(List<String> all, List<String> user) {
        if (all == null || all.isEmpty()) return true; // 조건 없음
        return all.stream().allMatch(user::contains);
    }



    /* 설명. Mono는 아무 데이터도 반환하지 않고, 비동기적으로 완료됨을 나타내는 반환타입 */
    private Mono<Void> onError(ServerWebExchange exchange, String errorMessage, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        exchange.getResponse().getHeaders().add("X-Deny-Reason", errorMessage);
        log.info("에러 메세지 : {}", errorMessage);
        return response.setComplete();
    }


//    @Override
//    public GatewayFilter apply(Config config) {
//        return ((exchange, chain) -> {
//            // 헤더 추출
//            ServerHttpRequest request = exchange.getRequest();
//            String path = request.getURI().getPath();
//
//            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
//                return onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED);    // HttpStatus.UNAUTHORIZED = 401에러
//            }
//            log.info("넘어온 auth 값 : {}", request.getHeaders().get(HttpHeaders.AUTHORIZATION));
//
//            // 토큰 추출
//            String bearerToken = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
//            String jwt = bearerToken.substring(7);
//            String subject = null;
//            Claims claims = null;
//            List<String> auths = null;
//            // sub 클레임 추출
//            /* 설명. 기본적으로 우리 서버에서 만들었고, 만료기간이 지나지 않았으며,
//             *      토큰 안에 'sub'라는 등록된 클레임이 존재하는지 확인
//             * */
//            try {
//
//                claims = Jwts.parser()
//                        .setSigningKey(tokenSecret)
//                        .parseClaimsJws(jwt)
//                        .getBody();
//
//                auths = (List<String>) claims.get("auth");
//
//                subject = claims.getSubject();
//            } catch (Exception e) {
//                return onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED);
//            }
//            log.info("sub 값 : {}", subject);
//
//            /* 설명. 토큰의 payload에 subject 클레임 자체가 없거나 내용물이 없는지 확인 */
//            if (subject == null || subject.isEmpty()) {
//                return onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED);
//            }
//
//            if (auths.contains("ROLE_ADMIN")) {
//                log.info("ADMIN 계정");
//            }
//
//
//            return chain.filter(exchange);
//        });
//    }

}
