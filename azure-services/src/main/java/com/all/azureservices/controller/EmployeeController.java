package com.all.azureservices.controller;

import com.all.azureservices.dto.Employee;
import com.all.azureservices.dto.TokenClass;
import com.all.azureservices.service.GetEmployeesByAPIMOauth2;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/apim")
public class EmployeeController {
    @Autowired
    private GetEmployeesByAPIMOauth2 getEmployeesByAPIMOauth2;

    @Value("#{${apim}}")
   HashMap<String, String> parameters;

    @GetMapping(value = "/publish", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Employee> getAllEmployees(
            ) {
       return getEmployeesByAPIMOauth2.getAllEmployees();
    }

    @GetMapping(value = "/map", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> getMapFromProperties(
    ) {
        return parameters;
    }

    @GetMapping(value = "/token", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAccesstoken(
    ) throws IOException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        var url = "https://login.microsoftonline.com/5d277b90-0469-4343-8339-e337788975e5/oauth2/v2.0/token";
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("grant_type", "client_credentials");
        parameters.put("client_id","2f1dba6d-99e0-44ef-989a-b6aab0f0ea2b");
        parameters.put("client_secret","client sectert");
        parameters.put("scope","api://1c39ced1-5e62-4841-916f-0c77fab7e42a/.default");
        String form = parameters.keySet().stream()
                .map(key -> key + "=" + URLEncoder.encode(parameters.get(key), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .headers("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(form)).build();
        HttpResponse<?> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String jsonString=response.body().toString();
        JsonNode node = mapper.readTree(jsonString);
        try
        {
            return node.get("access_token").asText();
        }
        catch (NullPointerException e)
        {
            return "not valid token";
        }
    }

}
