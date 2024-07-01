package com.example.gestion.tareas.D_infraestructure.security;

import com.example.gestion.tareas.D_infraestructure.security.config.CustomDaoAuthenticationProvider;
import com.example.gestion.tareas.D_infraestructure.security.config.MyUserDetailsService;
import com.example.gestion.tareas.D_infraestructure.security.filters.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class SecurityConfigurer {
    private final MyUserDetailsService myUserDetailsService;
    private final JwtRequestFilter jwtRequestFilter;

    @Autowired
    public SecurityConfigurer(MyUserDetailsService myUserDetailsService, JwtRequestFilter jwtRequestFilter){
        this.myUserDetailsService = myUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain securirSecurityFilterChain(HttpSecurity http) throws Exception{
        http.csrf().disable()
               /* .cors().disable()*/
                .authorizeRequests().requestMatchers(
                        "/swagger-ui/**",
                        "/swagger-resources/**",
                        "/webjars/**",
                        "/v2/**",
                        "/v2/api-docs",
                        "/api/v1/auth/**",
                        "/v2/api-docs",
                        "/v3/api-docs/**",
                        "/swagger-resorces",
                        "/swagger-resorces/**",
                        "/configuration/ui",
                        "/configuration/security",
                        "swagger-ui/**",
                        "/webjars/**",
                        "/swagger-ui.html",
                        "/h2-console/**"
                ).permitAll()
                .requestMatchers(HttpMethod.POST,"/api/v1/authenticate").permitAll()
                .anyRequest().authenticated()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(
                "/swagger-ui/**", "/v3/api-docs/**",
                "/v2/api-docs/**", "/swagger.json",
                "/swagger-resources/**", "/webjars/**",
                "/configuration/ui", "/h2-console/**"
        );
    }



    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return new ProviderManager(Collections.singletonList(authenticationProvider()));
    }
    @Bean
    public AuthenticationProvider authenticationProvider(){
        CustomDaoAuthenticationProvider provider = new CustomDaoAuthenticationProvider();
        provider.setUserDetailsService(myUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
    @Bean
    public static PasswordEncoder passwordEncoder(){
        //return NoOpPasswordEncoder.getInstance();
        String encodingId = "bcrypt";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(encodingId, new BCryptPasswordEncoder());

        return new DelegatingPasswordEncoder(encodingId, encoders);
    }
}
