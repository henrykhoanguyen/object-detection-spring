package com.take.home.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "image_objects")
public class ImageObjects {
    @EmbeddedId
    ImageObjectsKey id;

    @ManyToOne
    @MapsId("imageId")
    @JoinColumn(name = "image_id")
    Image image;

    @ManyToOne
    @MapsId("objectId")
    @JoinColumn(name = "object_id")
    Object object;
}
