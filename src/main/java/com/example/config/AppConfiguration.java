package com.example.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author mgudelj
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.example"} ,excludeFilters = @ComponentScan.Filter(type= FilterType.REGEX,pattern="com\\.example\\.initializer\\..*") )
@EnableJpaRepositories(basePackages = "com.example.dao")
@EntityScan(basePackages = "com.example.model")
public class AppConfiguration {

}

