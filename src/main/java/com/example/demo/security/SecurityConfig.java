package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

import ch.qos.logback.core.encoder.Encoder;
import net.bytebuddy.implementation.bind.annotation.BindingPriority;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
    private Http403ForbiddenEntryPoint forbiddenEntryPoint;

    @Bean
    public Http403ForbiddenEntryPoint forbiddenEntryPoint() {
        return new Http403ForbiddenEntryPoint();
    }
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.antMatchers("/dashboard")
			.hasRole("USER")
			.antMatchers("/","/**").permitAll()
		.and()
			.formLogin()
			.loginPage("/login")
			.defaultSuccessUrl("/dashboard",true)
		.and()
			.logout()
			.logoutSuccessUrl("/")
		.and()
			.httpBasic()
            .authenticationEntryPoint(forbiddenEntryPoint)
        .and()
            .csrf().disable();
	}
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.userDetailsService(userDetailsService)
			.passwordEncoder(encoder());
	}
}
