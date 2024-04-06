package com.take.home.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document("images")
public class Image {
    @Id
    private Long id;
    private String metadata;
    private String label;
    private String imageUrl;
    private List<String> detectedObjects;
}
