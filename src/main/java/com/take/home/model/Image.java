package com.take.home.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Set;

@Builder
@Getter
@Entity
public class Image {
    @Id
    @Column(name="image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String metadata;
    private String label;
    private String imageUrl;

    @ManyToMany
    @JoinTable(
            name = "image_object",
            joinColumns = @JoinColumn(name = "image_id"),
            inverseJoinColumns = @JoinColumn(name = "object_id")
    )
    private Set<Object> objects;
}
