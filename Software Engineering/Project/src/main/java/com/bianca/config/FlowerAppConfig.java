package com.bianca.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.bianca"})
public class FlowerAppConfig {

    @Bean
    public InternalResourceViewResolver viewResolver(){
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("WEB-INF/view/");
        viewResolver.setSuffix(".jsp");

        return viewResolver;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(){

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());
        return jdbcTemplate;
    }

    @Bean
    public DataSource dataSource(){

        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        // set the db info to the datasource object
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/florariedb?useSSL=false");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

        return dataSource;
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
