package com.c2psi.businessmanagement.config;

import com.c2psi.businessmanagement.services.auth.ApplicationUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.c2psi.businessmanagement.utils.Constants.*;
import static com.c2psi.businessmanagement.utils.pos.userbm.UserBMApiConstant.CREATE_USERBM_ENDPOINT;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private ApplicationUserDetailsService applicationUserDetailsService;
    private ApplicationRequestFilter applicationRequestFilter;

    @Autowired
    public SecurityConfiguration(ApplicationUserDetailsService applicationUserDetailsService,
                                 ApplicationRequestFilter applicationRequestFilter) {
        this.applicationUserDetailsService = applicationUserDetailsService;
        this.applicationRequestFilter = applicationRequestFilter;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(applicationUserDetailsService).passwordEncoder(passwordEncoder());
        //auth.userDetailsService(applicationUserDetailsService).passwordEncoder(null);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests()
                .antMatchers("/swagger-ui.html",
                        "/**/v2/api-docs",
                        "/swagger-resources",
                        "/swagger-resources/**",
                        "/configuration/ui",
                        "/configuration/security",
                        "/webjars/**",
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        APP_ROOT,
                        TEST_ENDPOINT,
                        AUTHENTICATION_ENDPOINT,
                        PERSONS_IMAGE_UPLOAD_ENDPOINT)
                .permitAll()
                .anyRequest()
                .authenticated()
                //.permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf()
                .disable();
        http.addFilterBefore(applicationRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

//            "/**/v2/api-docs",
//            "/swagger-resources",
//            "/swagger-resources/**",
//            "/configuration/ui",
//            "/configuration/security",
//            "/swagger-ui.html",
//            "/webjars/**",
//            "/v3/api-docs/**",
//            "/swagger-ui/**"

//    @Bean
//    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests((requests) -> requests
//                        .requestMatchers("/openapi/openapi.yml").permitAll()
//                        .anyRequest().authenticated())
//                .httpBasic();
//        return http.build();
//    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception{
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
