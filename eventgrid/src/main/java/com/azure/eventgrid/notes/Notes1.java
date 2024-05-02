package com.azure.eventgrid.notes;

public class Notes1 {
    /*
    1. in storage account will get
    2after storage account container send message to queue. we will listen that event-grid queue listener
    .
    2. After in event grid queue listener send message to internal queue listener.
    3. Then in internal queue listener we will call our service class
    4. in service class we will persist data in database
    5. The file we given to container is
    k1madn
k2madh
k3madn

fixed file.

How to create eventgrid subscription

1. create storage account with 2 containers one for upload file and one for after receiving file we will move that file archive state
2. create service bus with 2 queues 1 for normal queue 2. eventgrid queue
3. in storage account go to events click add event-grid subscription and give some defaults, create one system topic.
4. in endpoint select service bus queue. and then select what we create previously service bus name space and queue.
5. in filters enable subject filtering and in place of starts with give "/blobServices/default/containers/nouseofme/blobs/"


     */
}
