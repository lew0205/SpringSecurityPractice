package study.securityPrac.global.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import study.securityPrac.global.security.handler.CustomAccessDeniedHandler;
import study.securityPrac.global.security.handler.CustomAuthenticationEntryPointHandler;
import study.securityPrac.global.security.jwt.JwtAuthenticationFilter;
import study.securityPrac.global.security.jwt.JwtExceptionFilter;
import study.securityPrac.global.security.jwt.JwtRequestFilter;
import study.securityPrac.global.security.jwt.JwtTokenProvider;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;
    private final JwtExceptionFilter jwtExceptionFilter;
    private final JwtTokenProvider jwtTokenProvider;

    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
            .cors()
            .and()
            .httpBasic().disable() // rest api만을 고려하여 기본 설정 해제
            .csrf().disable() // csrf 보안 토큰 disable처리
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 기반 인증이므로 세션 사용X
            .and()
            .authorizeHttpRequests() // 요청에 대한 사용권한 체크
            .requestMatchers("/admin/**").hasRole("ROLE_ADMIN")
            .requestMatchers("/user/**").hasRole("ROLE_USER")
            .requestMatchers("/members/**").permitAll()
            .requestMatchers("/**").denyAll() // 허가되지 않은 API는 접근 불가
            .and()
            .exceptionHandling()
            .accessDeniedHandler(new CustomAccessDeniedHandler())
            .authenticationEntryPoint(new CustomAuthenticationEntryPointHandler())
            .and()
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(jwtExceptionFilter,JwtRequestFilter);
        return http.build();
    }

    // 암호화에 필요한 PasswordEncoder를 Bean에 등록
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
