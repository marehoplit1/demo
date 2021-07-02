package com.example.security;

import com.example.config.DccPmsProperties;
import com.example.service.UserService;
import com.example.utils.YamlUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.io.IOException;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder,
                                                 DccPmsProperties dccProperties, UserService userService,
                                                 ResourceLoader resourceLoader) throws IOException {

        final InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        final List<SecurityUser> users = YamlUtils.readObjectsFromFile(resourceLoader.getResource(dccProperties.getSecurity().getUsersInitFilePath()).getInputStream(), SecurityUser.class);

        users.forEach(u -> {
            manager.createUser(User.withUsername(u.getName())
                    .password(passwordEncoder.encode(u.getPassword()))
                    .roles(u.getRole().name())
                    .build());
        });

        users.forEach(u -> {
            com.example.model.User user = new com.example.model.User();
            user.setUsername(u.getUsername());
            user.setPassword(passwordEncoder.encode(u.getPassword()));
            user.setUserRole(u.getRole());
            user.setFirstName(u.getName());
            user.setLastName(u.getLastname());
            userService.save(user);
        });


        return manager;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/api/rest/v1/users/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
