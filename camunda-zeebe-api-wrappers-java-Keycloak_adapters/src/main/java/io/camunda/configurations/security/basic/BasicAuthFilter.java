package io.camunda.configurations.security.basic;

import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Predicate;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationConverter;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

public class BasicAuthFilter extends OncePerRequestFilter {

	private static final Logger LOGGER = LoggerFactory.getLogger(BasicAuthFilter.class);

	static <T> Predicate<Object> caase(Class<T> clazz, Consumer<T> consumer) {
		return obj -> {
			if (clazz.isInstance(obj)) {
				consumer.accept(clazz.cast(obj));
				return true;
			}
			return false;
		};
	}

	@SafeVarargs
	private static void handleException(Exception e, Predicate<Object>... predicates) {
		for (Predicate<Object> predicate : predicates)
			if (predicate.test(e))
				return;
	}

	BasicAuthenticationConverter authenticationConverter = new BasicAuthenticationConverter();

	@Autowired
	private UserDetailsService userDetailService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			UsernamePasswordAuthenticationToken authRequest = this.authenticationConverter.convert(request);
			if (authRequest == null) {
				LOGGER.error("Did not process authentication request since failed to find "
						+ "username and password in Basic Authorization header");
				filterChain.doFilter(request, response);
				return;
			}
			String username = authRequest.getName();
			LOGGER.info("Found username {} in Basic Authorization header", username);

			UserDetails userDetails = userDetailService.loadUserByUsername(username);
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
					null, userDetails.getAuthorities());
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

			SecurityContextHolder.getContext().setAuthentication(authentication);

		} catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException
				| IllegalArgumentException e) {

			SecurityContextHolder.clearContext();
			handleException(e,
					caase(MalformedJwtException.class, ex -> LOGGER.error("Invalid JWT token: {}", ex.getMessage())),
					caase(IllegalArgumentException.class,
							ex -> LOGGER.error("JWT claims string is empty: {}", ex.getMessage())),
					caase(UnsupportedJwtException.class,
							ex -> LOGGER.error("JWT token is unsupported: {}", ex.getMessage())),
					caase(SignatureException.class, ex -> LOGGER.error("Invalid JWT signature: {}", ex.getMessage())),
					caase(ExpiredJwtException.class, ex -> LOGGER.error("JWT token is expired: {}", ex.getMessage())));
		} catch (Exception e) {
			LOGGER.error("Cannot set user authentication: {}", e.getMessage());
			SecurityContextHolder.clearContext();
		}

		filterChain.doFilter(request, response);
	}

}
