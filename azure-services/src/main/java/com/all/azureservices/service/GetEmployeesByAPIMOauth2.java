package com.all.azureservices.service;

import com.all.azureservices.dto.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GetEmployeesByAPIMOauth2 {
    @Autowired
    WebClient webClientEmployee;

    @Value("${employees.getEmployees}")
    private String getAllEmployeesURL;

    @Value("${retryCount}")
    private long retryCount;

    @Value("${retryFixedDelayInSec}")
    private long retryFixedDelayInSec;

    @Value("${oauthURl}")
    private String oauthURl;

    @Value("#{${apim}}")
    HashMap<String, String> parameters;


    private String getAccessToken() {
        String form = parameters.keySet().stream()
                .map(key -> key + "=" + URLEncoder.encode(parameters.get(key), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(oauthURl))
                .headers("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(form)).build();
        HttpResponse<?> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = response.body().toString();
        JsonNode node = null;
        try {
            node = mapper.readTree(jsonString);
            return node.get("access_token").asText();
        } catch (JsonProcessingException | NullPointerException e) {
            return "not valid token";
        }

    }

    private Mono<List<Employee>> getAllEmployeesFromAPIMOATUH2() {
        String methodName = "getProductDetailsByPrdNoAndBanner";
        log.info("MethodName: {} | Fetching OTM Product List from OTM Product Service", methodName);
        Mono<List<Employee>> otmProductList = null;
        otmProductList = webClientEmployee
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(getAllEmployeesURL)
                        .build())
                .headers(h -> h.setBearerAuth(getAccessToken()))
                .retrieve()
                .onStatus(
                        HttpStatus::isError,
                        response ->
                                Mono.error(
                                        new WebClientResponseException(
                                                response.statusCode().value(),
                                                response.statusCode().getReasonPhrase(),
                                                null,
                                                null,
                                                null)))
                .bodyToMono(new ParameterizedTypeReference<List<Employee>>() {
                })
                .retryWhen(
                        Retry.fixedDelay(retryCount, Duration.ofSeconds(retryFixedDelayInSec))
                                .filter(this::is5xxServerError)
                                .onRetryExhaustedThrow(
                                        (retryBackoffSpec, retrySignal) ->
                                                new Exception(retrySignal.failure().getMessage())))
                .doOnError(
                        e ->
                                log.error("MethodName: {} | Error receiving OTM Product List, Error: ",
                                        methodName,
                                        e));
        return otmProductList;
    }


    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        try {
            employees = getAllEmployeesFromAPIMOATUH2().block();
        } catch (Exception e) {
            log.error("Error while getting product: {}");
        }
        return employees;
    }

    private boolean is5xxServerError(Throwable throwable) {
        return (throwable instanceof WebClientResponseException
                && ((WebClientResponseException) throwable).getStatusCode().is5xxServerError())
                || (throwable instanceof TimeoutException);
    }


}
