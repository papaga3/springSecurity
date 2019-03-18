package com.example.springSecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.springSecurity.services.MyUserDetailService;
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	@Autowired
	MyUserDetailService userDetailService;
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception{
		/*auth.inMemoryAuthentication()
			.withUser("user").password(passwordEncoder.encode("password")).
			authorities("ROLE_USER");*/
		auth.userDetailsService(userDetailService);
	}
	
	@Override
	public void configure(HttpSecurity httpS) throws Exception{
		httpS.csrf().disable()
			.authorizeRequests()
				.antMatchers("/").permitAll()
				.antMatchers("/register").permitAll()
				.antMatchers("/demo/**").permitAll()
				.antMatchers("/reward.html").hasAuthority("ROLE_USER")
				.antMatchers("/user/**").hasAnyAuthority("ROLE_USER")
				.anyRequest().authenticated()	
			.and()
			.formLogin().permitAll()
			.and().logout().permitAll();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
