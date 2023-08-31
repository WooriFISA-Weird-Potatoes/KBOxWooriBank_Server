package com.woorifisa.kboxwoori.global.config.security;

import com.woorifisa.kboxwoori.domain.user.service.PrincipalDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PrincipalDetailService principalDetailService;

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(principalDetailService).passwordEncoder(encoder());
    }

    protected void configure(HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/","/api/login", "/api/users/join", "/api/users/check").permitAll()
                .antMatchers("/api/users/**").hasRole("USER")
                .antMatchers("/api/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("/api/login-proc")
                .usernameParameter("userId")
                .passwordParameter("password")
                .loginPage("/api/login")
                .defaultSuccessUrl("/")
                .failureUrl("/api/login")
                .and()
                .logout()
                .logoutUrl("/api/users/logout")
                .logoutSuccessHandler(configSuccessHandler())
                .invalidateHttpSession(true).deleteCookies("JSESSIONID");
    }

    private LogoutSuccessHandler configSuccessHandler() {
        return (request, response, authentication) -> {
            response.sendRedirect("/");
        };
    }

}
