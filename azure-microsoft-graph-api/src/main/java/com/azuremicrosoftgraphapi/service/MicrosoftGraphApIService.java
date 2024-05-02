package com.azuremicrosoftgraphapi.service;

import com.microsoft.graph.models.User;
import com.microsoft.graph.requests.GraphServiceClient;
import com.microsoft.graph.requests.UserCollectionPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
@Slf4j
public class MicrosoftGraphApIService {

    @Autowired
    public GraphServiceClient getGraphServiceClientByApplicationPermission;

    @Autowired
    public GraphServiceClient getGraphServiceClientByDelegatedPermission;

    public UserCollectionPage getUserInfoByApplicationPermission() {
        return getGraphServiceClientByApplicationPermission
                .users().buildRequest()
                .select("displayName,mail,userPrincipalName")
                .get();
        // return microsoftGraphApIService.getUserInfo();
    }

    public User getUserByDelegatedPermission() throws Exception {
        // Ensure client isn't null
        if (getGraphServiceClientByDelegatedPermission == null) {
            throw new Exception("Graph has not been initialized for user auth");
        }

        return getGraphServiceClientByDelegatedPermission.me()
                .buildRequest()
                .select("displayName,mail,userPrincipalName")
                .get();
    }


}
