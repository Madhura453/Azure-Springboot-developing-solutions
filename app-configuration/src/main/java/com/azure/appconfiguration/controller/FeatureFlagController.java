package com.azure.appconfiguration.controller;

import com.azure.spring.cloud.feature.manager.FeatureManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/madhura/alone")
@Slf4j
public class FeatureFlagController {

    @Autowired
    private FeatureManager featureManager;


    @GetMapping(value = "/feature-flag")
    private String getMeesage() {

        if(featureManager.isEnabledAsync("login").block())
        {
            return "feature flag is enabled";
        }

        return "feature flag is not enabled";
    }


}
