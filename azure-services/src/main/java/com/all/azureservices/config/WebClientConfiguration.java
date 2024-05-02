package com.all.azureservices.config;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;

@Configuration
@Slf4j
public class WebClientConfiguration {

    @Value("${employees.url}")
    private String employeesBaseUrl;

    @Value("${employees.subscriptionKeyName}")
    private String subscriptionKeyName;

    @Value("${employees.subscriptionKeyValue}")
    private String subscriptionKeyValue;

    @Bean
    public WebClient webClientEmployee() throws SSLException {
        final SslContext sslContext;

        sslContext =
                SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();

        HttpClient httpClient = HttpClient.create().secure(t -> t.sslContext(sslContext));
        ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);
        log.info("Preparing httpClient with baseUrl: {}, Subscription Key: {}, Value: {}",
                employeesBaseUrl,
                subscriptionKeyName,
                subscriptionKeyValue);

        return WebClient.builder()
                .baseUrl(employeesBaseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(subscriptionKeyName, subscriptionKeyValue)
                .clientConnector(connector)
                .build();
    }
}