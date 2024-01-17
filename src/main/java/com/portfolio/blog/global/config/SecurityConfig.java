package com.portfolio.blog.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // csrf 사용안함
                .csrf(AbstractHttpConfigurer::disable)

                // session 사용안함
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // form login 제거
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests((authorize) ->
                        authorize.anyRequest().permitAll());

//                .exceptionHandling((exceptionHandling) ->
//                        exceptionHandling
//                                .authenticationEntryPoint(restAuthenticationEntryPoint)
//                                .accessDeniedHandler(new RestAccessDeniedHandler()));

        // oauth2 login 설정
        // oauth2 login 을 사용하면 기본적으로 /login 으로 redirect 된다.
        // 이를 막기 위해 oauth2Login 을 사용하고, /api/v1/oauth2/authorization 으로 redirect 된다.
        // 이후, oauth2Login 의 successHandler 를 통해 로그인 성공시 처리를 진행한다.
        // 이후, oauth2Login 의 userInfoEndpoint 를 통해 사용자 정보를 가져온다.
        //
//                .oauth2Login((oauth2Login) ->
//                        oauth2Login
//                        .successHandler(loginSuccessHandler)
//                        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
//                        .userService(customOauth2Service))
//                        .authorizationEndpoint(authorizationEndpointConfig -> authorizationEndpointConfig
//                        .baseUri("/api/v1/oauth2/authorization")));

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }



}
