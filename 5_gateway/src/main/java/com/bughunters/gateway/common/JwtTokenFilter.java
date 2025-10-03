package com.bughunters.gateway.common;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class JwtTokenFilter extends AbstractGatewayFilterFactory<JwtTokenFilter.Config> {
//    private Environment env;

    @Value(value = "${token.secret}")
    private String tokenSecret;

    public JwtTokenFilter() {
        super(Config.class);
    }

    public static class Config {
    }


    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            // 헤더 추출
            ServerHttpRequest request = exchange.getRequest();
            String path = request.getURI().getPath();

            if (!path.equals("/login")) {
                if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    return onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED);    // HttpStatus.UNAUTHORIZED = 401에러
                }
                log.info("넘어온 auth 값 : {}", request.getHeaders().get(HttpHeaders.AUTHORIZATION));

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

                    subject = claims.getSubject();
                } catch (Exception e) {
                    return onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED);
                }
                log.info("sub 값 : {}", subject);

                /* 설명. 토큰의 payload에 subject 클레임 자체가 없거나 내용물이 없는지 확인 */
                if (subject == null || subject.isEmpty()) {
                    return onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED);
                }

                if(auths.contains("ROLE_ADMIN")){
                    log.info("ADMIN 계정");
                }

//                List<String> auths =

            }
            return chain.filter(exchange);
        });
    }

    /* 설명. Mono는 아무 데이터도 반환하지 않고, 비동기적으로 완료됨을 나타내는 반환타입 */
    private Mono<Void> onError(ServerWebExchange exchange, String errorMessage, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        log.info("에러 메세지 : {}", errorMessage);
        return response.setComplete();
    }

}
