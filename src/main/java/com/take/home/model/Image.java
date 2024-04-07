package com.take.home.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Getter
@Setter
@Builder
@Document(collection = "images")
public class Image {
    @Field("_id")
    private long id;
    private String metadata;
    private String label;
    private String imageUrl;
    private List<String> detectedObjects;
}
