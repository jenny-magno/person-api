package com.jnmagno.restservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SpringSecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

      // default to in-memory user authentication
      auth.inMemoryAuthentication()
        .withUser("user").password("{noop}password").roles("USER");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
          .httpBasic()
          .and()
          .authorizeRequests()
          .antMatchers(HttpMethod.GET, "/api/v1/persons").hasRole("USER")
          .antMatchers(HttpMethod.GET, "/api/v1/persons/**").hasRole("USER")
          .antMatchers(HttpMethod.POST, "/api/v1/persons").hasRole("USER")
          .antMatchers(HttpMethod.PUT, "/api/v1/persons/**").hasRole("USER")
          .antMatchers(HttpMethod.DELETE, "/api/v1/persons/**").hasRole("USER")
          .and()
          .csrf().disable()
          .formLogin().disable();
    }

}
