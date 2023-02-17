package io.camunda.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class Utils {

	private static final Logger LOG = LoggerFactory.getLogger(Utils.class);

	public Authentication getAuthenticatedUser() {
		LOG.info("Authenticated User: {}", SecurityContextHolder.getContext().getAuthentication());
		return SecurityContextHolder.getContext().getAuthentication();

	}

}
