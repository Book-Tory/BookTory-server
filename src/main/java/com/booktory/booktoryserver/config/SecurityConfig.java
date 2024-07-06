package com.booktory.booktoryserver.config;

import com.booktory.booktoryserver.Users.filter.CustomLogoutFilter;
import com.booktory.booktoryserver.Users.filter.JWTFilter;
import com.booktory.booktoryserver.Users.filter.JWTUtil;
import com.booktory.booktoryserver.Users.filter.LoginFilter;
import com.booktory.booktoryserver.Users.handler.CustomSuccessHandler;
import com.booktory.booktoryserver.Users.mapper.RefreshMapper;
import com.booktory.booktoryserver.Users.service.CustomOAuth2UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    // OAuth2 클라이언트 설정 값 가져오기
    @Value("${NAVER_OAUTH_CLIENT_ID}")
    private String naverClientId;

    @Value("${NAVER_OAUTH_CLIENT_SECRET}")
    private String naverClientSecret;

    @Value("${GOOGLE_OAUTH_CLIENT_ID}")
    private String googleClientId;

    @Value("${GOOGLE_OAUTH_CLIENT_SECRET}")
    private String googleClientSecret;


    private final AuthenticationConfiguration authenticationConfiguration;

    private final JWTUtil jwtUtil;

    private final RefreshMapper refreshMapper;

    private final CustomSuccessHandler customSuccessHandler;

    private final CustomOAuth2UserService customOAuth2UserService;


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf((csrf) -> csrf.disable());

        http
                .cors((cors) -> cors.configurationSource(corsConfigurationSource()));

//        http
//                .cors(cors -> cors.configurationSource(new CorsConfigurationSource() {
//                    @Override
//                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
//                        CorsConfiguration config = new CorsConfiguration();
//                        config.setAllowedOrigins(Arrays.asList("http://localhost:5173", "http://52.78.9.158:5173", "http://52.78.9.158:80",  "http://52.78.9.158"));
//                        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//                        config.setAllowCredentials(true);
//                        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "access", "refresh"));
//                        config.setExposedHeaders(Arrays.asList("Authorization", "access", "refresh"));
//                        config.setMaxAge(3600L); // 1시간
//                        return config;
//                    }
//                }));


        http
                .sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http
                .authorizeHttpRequests((auth) -> auth

                        .requestMatchers("/login**", "/**", "/api/users/auth/register", "/api/reissue", "/api/product_shop/list",
                                "/api/used-books/list",  "/api/used-books/detail/**", "/api/product_shop/detail/**", "/api/qna/**","/api/stories/mystories",
                                "/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**").permitAll()
                        .requestMatchers("/test").hasRole("USER")
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .anyRequest().authenticated());

        http
                .formLogin((formLogin) -> formLogin.disable());

        http
                .httpBasic((httpBasic) -> httpBasic.disable());

        //oauth2
        http
                .oauth2Login((oauth2) -> oauth2
                        .userInfoEndpoint((userInfoEndpointConfig) -> userInfoEndpointConfig
                                .userService(customOAuth2UserService))
                        .successHandler(customSuccessHandler));
        http
                .addFilterBefore(new CorsFilter(corsConfigurationSource()), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JWTFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil, refreshMapper), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new CustomLogoutFilter(jwtUtil, refreshMapper), LogoutFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://52.78.9.158:5173", "http://localhost:5173", "http://54.91.40.23:5173", "http://booktoryalb-924622636.ap-northeast-2.elb.amazonaws.com"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "access", "refresh"));
        configuration.setExposedHeaders(Arrays.asList("Authorization", "access", "refresh"));
        configuration.setMaxAge(3600L); // 1시간

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
