package com.receiptfinanceapi.configurations;

import com.receiptfinanceapi.filters.JWTAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().configurationSource(request -> corsConfiguration())
                .and()
                .csrf().disable()
                .addFilterAfter(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/user/login").permitAll()
                .antMatchers(HttpMethod.POST, "/user/register").permitAll()
                .anyRequest().authenticated();
    }

    @Bean
    JWTAuthorizationFilter jwtAuthorizationFilter() {
        return new JWTAuthorizationFilter();
    }

    @Bean
    CorsConfiguration corsConfiguration() {
        CorsConfiguration corsConf = new CorsConfiguration().applyPermitDefaultValues();

        corsConf.addAllowedMethod(HttpMethod.DELETE);
        corsConf.addAllowedMethod(HttpMethod.PUT);

        return corsConf;
    }

}
