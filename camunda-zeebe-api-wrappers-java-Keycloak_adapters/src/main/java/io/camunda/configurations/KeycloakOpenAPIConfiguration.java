package io.camunda.configurations;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.camunda.properties.KeycloakProperties;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.Scopes;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
@OpenAPIDefinition
@ConditionalOnProperty(name = "keycloak.enabled", havingValue = "true", matchIfMissing = false)
public class KeycloakOpenAPIConfiguration {
 
    private static final String OAUTH_SCHEME_NAME = "authorization";
    private static final String PROTOCOL_URL_FORMAT = "%s/realms/%s/protocol/openid-connect";
 
    @Bean
    public OpenAPI customOpenApi(KeycloakProperties keycloakProperties) {
        return new OpenAPI()
                .components(new Components() 
                        		.addSecuritySchemes(OAUTH_SCHEME_NAME, 
                        				createOAuthScheme(keycloakProperties)))               
                .addSecurityItem(new SecurityRequirement().addList(OAUTH_SCHEME_NAME));
    }
 
    private SecurityScheme createOAuthScheme(KeycloakProperties properties) {
        OAuthFlows flows = createOAuthFlows(properties); 
 
        return new SecurityScheme() 
                .type(SecurityScheme.Type.OAUTH2)
                .flows(flows);
    }
 
    private OAuthFlows createOAuthFlows(KeycloakProperties properties) {
        OAuthFlow flow = createAuthorizationCodeFlow(properties);
 
        return new OAuthFlows()
                .authorizationCode(flow);
    }
 
    private OAuthFlow createAuthorizationCodeFlow(KeycloakProperties properties) {
        var protocolUrl = String.format(PROTOCOL_URL_FORMAT, properties.getAuthServerUrl(), properties.getRealm());
 
        return new OAuthFlow()
                .authorizationUrl(protocolUrl + "/auth")
                .tokenUrl(protocolUrl + "/token")                
                .scopes(new Scopes().addString("openid", ""));
    }
}