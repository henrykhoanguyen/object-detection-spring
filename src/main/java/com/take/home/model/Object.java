package com.take.home.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Builder
@Getter
@Entity
public class Object {
    @Id
    @Column(name = "object_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "image")
    private Set<Image> images;
}
