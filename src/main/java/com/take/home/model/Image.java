package com.take.home.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document
public class Image {
    @Id
    private Long id;
    private String metadata;
    private String label;
    private String identifier;
    private List<String> detectedObjects;
}
