package org.eventgrid.events.to.HTTPendpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.eventgrid.customization.EventGridSubscriber;
import com.microsoft.azure.eventgrid.models.EventGridEvent;
import com.microsoft.azure.eventgrid.models.SubscriptionValidationEventData;
import com.microsoft.azure.eventgrid.models.SubscriptionValidationResponse;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import java.io.IOException;
import java.util.Optional;

/**
 * Azure Functions with HTTP Trigger.
 */
public class SubscribeEventGridEventsToHTTPEndpoint {
    /**
     * This function listens at endpoint "/api/HttpExample". Two ways to invoke it using "curl" command in bash:
     * 1. curl -d "HTTP Body" {your host}/api/HttpExample
     * 2. curl "{your host}/api/HttpExample?name=HTTP%20Query"
     */
    @FunctionName("SubscribeEventGridEventsToHTTPEndpoint")
    public HttpResponseMessage subscribeEventGridEventsToHTTPEndpoint(
            @HttpTrigger(
                    name = "req",
                    methods = {HttpMethod.GET, HttpMethod.POST},
                    authLevel = AuthorizationLevel.ANONYMOUS)
            HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {

        String req = request.getBody().get();

        EventGridSubscriber subscriber = new EventGridSubscriber();
        EventGridEvent[] events = null;
        try {
            events = subscriber.deserializeEventGridEvents(req);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (EventGridEvent eventGridEvent : events) {
            if (eventGridEvent.data() instanceof SubscriptionValidationEventData) {
                SubscriptionValidationEventData validationEventData = (SubscriptionValidationEventData) eventGridEvent.data();
                context.getLogger().info("The validation code is" + validationEventData.validationCode());
                context.getLogger().info("The validation url is" + validationEventData.validationUrl());
                SubscriptionValidationResponse response = new SubscriptionValidationResponse()
                        .withValidationResponse(validationEventData.validationCode());
                return request.createResponseBuilder(HttpStatus.OK).body(response).build();
            }

            try {
                context.getLogger().info("The received event subject is" + new ObjectMapper().writeValueAsString(eventGridEvent.subject()));
                // String b=eventGridEvent.data();
                context.getLogger().info("The received event is" + new ObjectMapper().writeValueAsString(eventGridEvent.data()));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        return request.createResponseBuilder(HttpStatus.OK).body(new String(" ")).build();
    }
}
