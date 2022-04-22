package com.bianca.config;

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.crypto.password.*;

import javax.sql.*;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth
                .jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/catalog-admin").hasAuthority("ADMIN")
                .antMatchers("/wish").hasAnyAuthority("USER", "ADMIN")
                .anyRequest().permitAll()
                .and()
                    .formLogin().loginPage("/mylogin").loginProcessingUrl("/process-login").defaultSuccessUrl("/")
                    .and()
                    .httpBasic()
                    .and().logout().logoutSuccessUrl("/");
    }

}