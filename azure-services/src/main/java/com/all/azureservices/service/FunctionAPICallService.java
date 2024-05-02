package com.all.azureservices.service;

import com.all.azureservices.dto.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

@Service
@Slf4j
public class FunctionAPICallService {

//    @Value("${functionUrl}")
//    private String functionAPIUrl;

    @Autowired
    @Qualifier("webClientForFuctionAPICall")
    WebClient webClientForFuctionCall;

    @Value("${retryCount}")
    private long retryCount;

    @Value("${retryFixedDelayInSec}")
    private long retryFixedDelayInSec;

    public Mono<List<Employee>> getEmployeesByFunctionUrl() {
        String methodName = "getProductDetailsByPrdNoAndBanner";
        log.info("MethodName: {} | Fetching OTM Product List from OTM Product Service", methodName);
        Mono<List<Employee>> otmProductList = null;
        otmProductList = webClientForFuctionCall
                .get()
                .uri(uriBuilder -> uriBuilder
                        .build())
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


    public List<Employee> getAllEmployeesByFunctionUrl() {
        List<Employee> employees = new ArrayList<>();
        try {
            employees = getEmployeesByFunctionUrl().block();
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
