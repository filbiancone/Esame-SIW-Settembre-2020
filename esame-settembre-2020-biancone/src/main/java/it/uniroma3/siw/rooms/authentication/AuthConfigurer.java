package it.uniroma3.siw.rooms.authentication;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * The AuthConfiguration is a Spring Security Configuration.
 * It extends WebSecurityConfigurerAdapter, meaning that it provides the settings for Web security.
 */
@Configuration
@EnableWebSecurity
public class AuthConfigurer extends WebSecurityConfigurerAdapter {

	/**
     * This method provides the whole authentication and authorization configuration to use.
     */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.
		authorizeRequests()
		//public views
		/*
		 * TO DO: rimpiazza queste righe con quelle obbligatorie dell'admin e queste le
		 * racchiudi in anyRequest.permitAll()
		 * */
		.antMatchers(HttpMethod.GET, "/", "/index", "/login", "/reservation/form", "/rooms", "/room/{id}", "/css/**").permitAll()
		.antMatchers(HttpMethod.POST, "/login", "/reservation/form").permitAll()
		.anyRequest().authenticated()
		//login form
		.and().formLogin().defaultSuccessUrl("/admin", true)
        //logout form
        .and().logout().logoutUrl("/logout").logoutSuccessUrl("/index");
	}
}
