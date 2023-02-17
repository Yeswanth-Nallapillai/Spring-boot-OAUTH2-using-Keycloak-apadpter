package io.camunda.configurations.security.keycloak;

import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationEntryPoint;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@KeycloakConfiguration
@ConditionalOnProperty(name = "keycloak.enabled", havingValue = "true", matchIfMissing = false)
public class KeycloakSecurityConfiguration extends KeycloakWebSecurityConfigurerAdapter {

	private static final RequestMatcher PROTECTED_URLS = new OrRequestMatcher(
            new AntPathRequestMatcher("/v1/**")
    );
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		super.configure(http);
		http.authorizeRequests()
			.antMatchers("/api-docs", "/api-docs/**", "/swagger-ui/**", "/swagger-ui.html")			
			.permitAll()		
			.requestMatchers(PROTECTED_URLS).authenticated()
			.and().exceptionHandling().authenticationEntryPoint(authenticationEntryPoint(PROTECTED_URLS))
			.and().cors().disable()
			.csrf().disable().headers()			
			.frameOptions().disable().and().sessionManagement()			
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	protected AuthenticationEntryPoint authenticationEntryPoint(RequestMatcher protectedUrls) throws Exception {
		return new KeycloakAuthenticationEntryPoint(adapterDeploymentContext(), protectedUrls);
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(getKeycloakAuthenticationProvider());
	}

	private KeycloakAuthenticationProvider getKeycloakAuthenticationProvider() {
		KeycloakAuthenticationProvider authenticationProvider = keycloakAuthenticationProvider();
		var mapper = new SimpleAuthorityMapper();
		mapper.setConvertToUpperCase(true);
		authenticationProvider.setGrantedAuthoritiesMapper(mapper);

		return authenticationProvider;
	}

	@Bean
	@Override
	protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
		return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
	}
}
