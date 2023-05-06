package com.hana.securityinboard.global.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    public CorsFilter corsFilter;

    //TODO 일단은 다 열어두나 추후 처리할수 있으면 처리하기
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // 내 서버가 응답을 할 때 json을 자바스크립트에서 응답을 할 수 있게 처리할지를 설정하는것
        // (ajax 등..)
        config.setAllowCredentials(true);
        // 모든 ip에 응답허용
        config.addAllowedOrigin("*"); // e.g. http://domain1.com
        // 모든 헤더에 응답허용
        config.addAllowedHeader("*");
        // 모든 httpMethod에 응답허용
        config.addAllowedMethod("*");

        source.registerCorsConfiguration("/api/**", config);
        return new CorsFilter(source);
    }

}
