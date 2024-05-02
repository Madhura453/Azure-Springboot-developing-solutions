package com.azure.cosmosdb.service;

import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.models.CosmosItemRequestOptions;
import com.azure.cosmos.models.PartitionKey;
import com.azure.cosmosdb.Model.Course;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class NoSQlTriggersService {

    @Autowired
    private CosmosContainer container;

    public void timeStampParamereCheckingTriggerExecution(Course course) {
        // execution of trigger
        //this is trigger when adding course it will check timestamp parameter is there in body.
        // if not present then in cosmos container for that item it will add timestamp parameter

        // write list of trigger names
        List<String>  preTriggerName=List.of("preTriggerTimeStamp");
        CosmosItemRequestOptions option=new CosmosItemRequestOptions();
       option.setPreTriggerInclude(preTriggerName);
     container.createItem(course,new PartitionKey(course.getCourseId()),option);


    }
}
