package com.azuremicrosoftgraphapi.controller;

import com.azuremicrosoftgraphapi.service.MicrosoftGraphApIService;
import com.microsoft.graph.models.User;
import com.microsoft.graph.requests.GraphServiceClient;
import com.microsoft.graph.requests.UserCollectionPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/graph")
public class MicrosoftGraphApIController {

    @Autowired
    private MicrosoftGraphApIService microsoftGraphApIService;


    @GetMapping("/user-application-permission")
    public UserCollectionPage getUserInfo() {
       return microsoftGraphApIService
               .getUserInfoByApplicationPermission();
    }

    @GetMapping("/user-delegated-permission")
    public User getUser() throws Exception {
     return microsoftGraphApIService.getUserByDelegatedPermission();
    }


}
