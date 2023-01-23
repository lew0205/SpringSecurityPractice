package study.securityPrac.global.config.security;

import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import study.securityPrac.global.security.jwt.JwtAuthenticationFilter;
import study.securityPrac.global.security.jwt.JwtTokenProvider;

import java.util.List;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
            .httpBasic().disable() // rest api만을 고려하여 기본 설정 해제
            .csrf().disable() // csrf 보안 토큰 disable처리
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 기반 인증이므로 세션 사용X
            .and()
            .authorizeRequests() // 요청에 대한 사용권한 체크
            .requestMatchers("/admin/**").hasRole("ROLE_ADMIN")
            .requestMatchers("/user/**").hasRole("ROLE_USER")
            .requestMatchers("/**").permitAll() // 그 외 누구나 사용 가능
            .and()
            .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), JwtAuthenticationFilter.class);
        return http.build();
    }

    // 암호화에 필요한 PasswordEncoder를 Bean에 등록
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
