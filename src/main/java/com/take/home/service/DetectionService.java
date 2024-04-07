package com.take.home.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.take.home.config.ImaggaConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class DetectionService {

    private final ImaggaConfiguration configuration;

    private final String basicAuth;

    public DetectionService(ImaggaConfiguration configuration) {
        this.configuration = configuration;
        this.basicAuth = configuration.getBasicAuth();
    }

    public List<String> getObjectsDetected(String imageUrl) throws IOException {
        String url = configuration.getEndpointUrl() + "?image_url=" + imageUrl;
        URL urlObject = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();

        connection.setRequestProperty("Authorization", "Basic " + basicAuth);

        int responseCode = connection.getResponseCode();

        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader connectionInput = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String jsonResponse = connectionInput.readLine();

        connectionInput.close();

        return extractObjects(jsonResponse);
    }

    private static List<String> extractObjects(String jsonResponse) {
        List<String> tags = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode tagsNode = rootNode.path("result").path("tags");
            for (JsonNode tagNode : tagsNode) {
                String tag = tagNode.path("tag").path("en").asText();
                tags.add(tag);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return tags;
    }
}
