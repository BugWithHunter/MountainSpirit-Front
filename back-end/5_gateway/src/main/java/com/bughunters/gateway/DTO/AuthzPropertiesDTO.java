package com.bughunters.gateway.DTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "security.authz")
public class AuthzPropertiesDTO {
    private List<Rule> rules;

    @Getter
    @Setter
    public static class Rule {
        private String pattern;          // 예: /member-client/** (Ant 경로)
        private List<String> methods;    // 예: [GET, POST] (optional)
        private List<String> anyRole;    // 하나라도 포함되면 통과 (optional)
        private List<String> allRole;    // 모두 포함되어야 통과 (optional)
        private Boolean permitAll = false;
    }
}
