package com.take.home.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Image {
    @Id
    @Column(name = "image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String label;
    private String imageUrl;

    @OneToMany(mappedBy = "image", cascade = CascadeType.ALL)
    private List<Color> colors;

    @ManyToMany(mappedBy = "images", cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    private Set<Object> objects;
//    TODO: FIX THIS MISSING OBJECTS AND COLORS IN DB
}
