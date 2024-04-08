package com.take.home.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.take.home.config.ImaggaConfiguration;
import com.take.home.model.Color;
import com.take.home.repository.ColorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class DetectionService {

    private final ImaggaConfiguration configuration;

    @Autowired
    private ColorRepository colorRepository;

    private final String basicAuth;

    public DetectionService(ImaggaConfiguration configuration) {
        this.configuration = configuration;
        this.basicAuth = configuration.getBasicAuth();
    }

    public List<String> getDetectedObjects(String imageUrl) throws IOException {
        String url = configuration.getEndpointUrl() + "/tags?image_url=" + imageUrl;
        URL urlObject = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();

        connection.setRequestProperty("Authorization", "Basic " + basicAuth);

        int responseCode = connection.getResponseCode();

        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader connectionInput = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String detectedObjects = connectionInput.readLine();

        connectionInput.close();

        return extractObjectsFromJson(detectedObjects);
    }

    public String getLabel(String imageUrl) throws IOException {
        String url = configuration.getEndpointUrl() + "/categories/personal_photos?image_url=" + imageUrl;
        URL urlObject = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();

        connection.setRequestProperty("Authorization", "Basic " + basicAuth);

        int responseCode = connection.getResponseCode();

        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader connectionInput = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String imageLabel = connectionInput.readLine();

        connectionInput.close();

        return extractLabelFromJson(imageLabel);
    }

    public List<Color> getMetadata(String imageUrl) {
        String url = configuration.getEndpointUrl() + "/colors?image_url=" + imageUrl + "&overall_count=5&separated_count=0";
        String metadata = "";
        try {
            URL urlObject = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();

            connection.setRequestProperty("Authorization", "Basic " + basicAuth);

            int responseCode = connection.getResponseCode();

            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader connectionInput = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            metadata = connectionInput.readLine();

            connectionInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return extractColorsFromJson(metadata);
    }

    private List<String> extractObjectsFromJson(String detectedObjects) {
        List<String> tags = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(detectedObjects);
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

    private String extractLabelFromJson(String imageLabel){
        String label = "";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(imageLabel);
            JsonNode catNode = rootNode.path("result").path("categories");
            label = catNode.path("name").path("en").asText();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return label;
    }

    private List<Color> extractColorsFromJson(String metadata){
        List<Color> colors = new ArrayList<>();
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(metadata);
            JsonNode colorsNode = rootNode.path("result").path("colors").path("image_colors");
            for (JsonNode color : colorsNode) {
                String rgb = color.path("r").asText() + "," + color.path("g").asText() + "," + color.path("b").asText();
                String paletteColor = color.path("closest_palette_color").asText();
                String htmlCode = color.path("html_code").asText();
                String percentage = color.path("percent").asText();

                Color colorBuilder = Color.builder()
                        .rgb(rgb)
                        .paletteColor(paletteColor)
                        .htmlCode(htmlCode)
                        .percentage(percentage)
                        .build();

//                colorRepository.save(colorBuilder);
                colors.add(colorBuilder);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return colors;
    }
}