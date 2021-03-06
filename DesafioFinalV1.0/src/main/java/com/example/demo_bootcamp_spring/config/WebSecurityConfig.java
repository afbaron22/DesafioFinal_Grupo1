package com.example.demo_bootcamp_spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // configure AuthenticationManager so that it knows from where to load
        // user for matching credentials
        // Use BCryptPasswordEncoder
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/auth", "/register").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/api/v1/fresh-products/inboundorder").hasRole("REPRESENTATIVE")
                .antMatchers(HttpMethod.POST, "/api/v1/fresh-products/list").hasRole("REPRESENTATIVE")
                .antMatchers(HttpMethod.POST, "/api/v1/fresh-products/warehouse").hasRole("REPRESENTATIVE")
                .antMatchers("/api/v1/fresh-products/orders**").hasRole("BUYER")
                .antMatchers(HttpMethod.GET, "/api/v1/fresh-products").hasRole("BUYER")
                .antMatchers(HttpMethod.GET, "/api/v1/fresh-products/listOrder").hasRole("BUYER")
                .antMatchers(HttpMethod.GET, "/api/v1/fresh-products/listProduct").hasRole("BUYER")
                .antMatchers("/api/v1/fresh-products/orders").hasRole("BUYER")
                .antMatchers("api/v1/fresh-products/due-date").hasRole("REPRESENTATIVE")
                .anyRequest().authenticated().and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);



        httpSecurity.csrf().disable();
        httpSecurity.headers().frameOptions().disable();

        // Add a filter to validate the tokens with every request
        httpSecurity.addFilterAfter(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
