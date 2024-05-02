package com.azure.asb.topic.notes;

public class Notes {
    /*

    queue same as topic except multiple subscriptions

    <!--	this dependency> ment for autoconfiguration of
		spring.jms.servicebus-com.azure.spring.autoconfigure.jms.AzureServiceBusJMSProperties-->
<!--		<dependency>-->
<!--			<groupId>com.azure.spring</groupId>-->
<!--			<artifactId>azure-spring-boot-starter-servicebus-jms</artifactId>-->
<!--			<version>3.12.0</version>-->
<!--		</dependency>-->


 2 receive modes will be there
    azure.servicebus.subscription-receive-mode= PEEK_LOCK
#azure.servicebus.subscription-receive-mode= RECEIVE_AND_DELETE

peek-lock = won't delete message
RECEIVE_AND_DELETE= it will delete message from queue
async client has flatmap
sync client no flatmap
take care on disableAutoComplete()
return queueSubscriberAync.complete(message); it will delete message from queue if we given like
this disableAutoComplete(). otherwise no

if you mention disableAutoComplete() in ServiceBusClient then you need write
queueSubscriberAync.complete(message); message. otherwise it won't delete message in topic

duplicate detection= message id sending mandatory
duplicate-time window= after sending message in time span of time9(ex 2 minutes)
it will detect duplicate message. if detect it will reject message



     */


}
