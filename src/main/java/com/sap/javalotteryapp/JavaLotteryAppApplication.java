package com.sap.javalotteryapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@SpringBootApplication
@EnableOAuth2Sso
public class JavaLotteryAppApplication extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/participantForm", "/participantCreated", "/winner")
					.authenticated()
				.antMatchers("/", "/participants")
					.permitAll()
			.and()
				.formLogin();
		;
	}

	public static void main(String[] args) {
		SpringApplication.run(JavaLotteryAppApplication.class, args);
	}
}
