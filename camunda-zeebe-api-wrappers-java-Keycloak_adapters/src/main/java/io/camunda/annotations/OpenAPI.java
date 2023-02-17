package io.camunda.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;
import org.springframework.security.access.prepost.PreAuthorize;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Operation
@Tag(name = "")
@PreAuthorize("isAuthenticated()")
public @interface OpenAPI {

	@AliasFor(annotation = Tag.class)
	String name() default "";

	@AliasFor(annotation = Operation.class)
	SecurityRequirement[] security() default { @SecurityRequirement(name = "authorization") };

	@AliasFor(annotation = Operation.class)
	String summary() default "";

}
