package com.azuremicrosoftgraphapi.config;

import com.azure.identity.*;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.requests.GraphServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.function.Consumer;

import java.util.List;

@Configuration
public class MicrosoftGraphApIConfig {

    @Value("${app.clientId}")
    private String clientId;

    @Value("${app.tenantId}")
    private String tenantId;

    @Value("${app.clientSecret}")
    private String clientSecret;

    @Value("${app.graphUserScopes}")
    private List<String> scopes;

    @Bean
    public GraphServiceClient getGraphServiceClientByApplicationPermission()
    {
        //  Application permission GraphServiceClient
        ClientSecretCredential clientSecretCredential = new ClientSecretCredentialBuilder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .tenantId(tenantId)
                .build();

        TokenCredentialAuthProvider tokenCredentialAuthProvider = new TokenCredentialAuthProvider(Collections.singletonList("https://graph.microsoft.com/.default"), clientSecretCredential);

        GraphServiceClient graphClient =
                GraphServiceClient
                        .builder()
                        .authenticationProvider(tokenCredentialAuthProvider)
                        .buildClient();

        return graphClient;
    }



    @Bean
    public GraphServiceClient getGraphServiceClientByDelegatedPermission() {
       // delegated permission GraphServiceClient
       DeviceCodeCredential _deviceCodeCredential = new DeviceCodeCredentialBuilder()
                .clientId(clientId)
                .tenantId(tenantId)
               // .challengeConsumer(challenge)
                .build();

        final TokenCredentialAuthProvider authProvider =
                new TokenCredentialAuthProvider(scopes, _deviceCodeCredential);

        return GraphServiceClient.builder()
                .authenticationProvider(authProvider)
                .buildClient();
    }
}
