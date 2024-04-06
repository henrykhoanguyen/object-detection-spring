package com.take.home.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document("objects")
public class Object {
    @Id
    private Long id;
    private String name;
    private List<String> imageId;
}
