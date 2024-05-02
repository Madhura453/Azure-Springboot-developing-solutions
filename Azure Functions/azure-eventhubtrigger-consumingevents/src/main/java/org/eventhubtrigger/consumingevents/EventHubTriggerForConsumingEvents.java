//package org.eventhubtrigger.consumingevents;
//
//import com.azure.messaging.eventhubs.models.PartitionEvent;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.microsoft.azure.functions.annotation.*;
//import com.microsoft.azure.functions.*;
//import reactor.core.publisher.Flux;
//
//import java.util.*;
//
///**
// * Azure Functions with Event Hub trigger.
// */
//public class EventHubTriggerForConsumingEvents {
//    /**
//     * This function will be invoked when an event is received from Event Hub.
//     https://learn.microsoft.com/en-us/azure/azure-functions/functions-bindings-event-hubs-trigger
//     */
//    @FunctionName("EventHubTriggerForConsumingEvents")
//    public void eventHubTriggerForConsumingEvents(
//        @EventHubTrigger(name = "message", eventHubName = "general", connection = "EventhubConnection", consumerGroup = "$Default", cardinality = Cardinality.MANY) List<String> message,
//        final ExecutionContext context
//    ) {
//        context.getLogger().info("Java Event Hub trigger function executed.");
//        context.getLogger().info("Length:" + message.size());
//        message.stream().forEach(object->{
//            context.getLogger().info("the content of message is:"+object);
//        });
////        message.stream().forEach(object->{
////            try {
////                String body=new ObjectMapper().writeValueAsString(object);
////                context.getLogger().info("the content of message is:"+body);
////            } catch (JsonProcessingException e) {
////                throw new RuntimeException(e);
////            }
////
////        });
//
//    }
//
//
//
//}
