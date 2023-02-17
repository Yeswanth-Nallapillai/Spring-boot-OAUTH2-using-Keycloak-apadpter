package io.camunda.configurations.security.basic;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.camunda.properties.CUsers;

@Configuration
@EnableWebSecurity
@ConditionalOnProperty(name = "keycloak.enabled", havingValue = "false", matchIfMissing = true)
public class SecurityConfiguration {

	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfiguration.class);

	private final CUsers users;

	public SecurityConfiguration(CUsers users) {
		super();
		this.users = users;
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Bean
	BasicAuthFilter basicAuthenticationFilter() {
		return new BasicAuthFilter();
	}

	@Bean
	CustomAuthenticationEntryPoint entryPoint() {
		return new CustomAuthenticationEntryPoint();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeHttpRequests()
				.antMatchers("/api-docs", "/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll().anyRequest()
				.authenticated().and().httpBasic().and().exceptionHandling().authenticationEntryPoint(entryPoint())
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(basicAuthenticationFilter(), BasicAuthenticationFilter.class);

		return http.build();
	}
	
	@Bean
	UserDetailsService userDetailsService() {
		List<UserDetails> userDetails = this.users.getUsers().stream()
				.map(user -> User.builder().username(user.getUsername())
						.password(passwordEncoder().encode(user.getPassword()))
						.roles(user.getRoles().toArray(String[]::new)).build())
				.collect(Collectors.toList());
		LOGGER.info("User details : {}", userDetails);
		return new InMemoryUserDetailsManager(userDetails);
	}

}
