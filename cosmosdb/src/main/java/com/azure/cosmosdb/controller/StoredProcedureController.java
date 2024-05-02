package com.azure.cosmosdb.controller;

import com.azure.cosmos.models.CosmosStoredProcedureResponse;
import com.azure.cosmosdb.Model.Course;
import com.azure.cosmosdb.service.NoSqlStoredProcedureService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class StoredProcedureController {

    @Autowired
    private NoSqlStoredProcedureService storedProcedureService;

    @GetMapping("/noparam")
    public CosmosStoredProcedureResponse executeStoredProcedureWithoutparameters
            (@RequestParam("stopname") String stopname)
    {
        return storedProcedureService.executeStoredProcedure(stopname);
    }


    @GetMapping("/parm")
    public CosmosStoredProcedureResponse executeStoredProcedureWithparameters
            (@RequestParam("stopname") String stopname, @RequestBody List<Course> courseList) throws JsonProcessingException {
        return storedProcedureService.executeStoredProcedureWithparams(stopname, Collections.singletonList(courseList));
    }


}
