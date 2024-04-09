package com.take.home.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class ImageObjectsKey implements Serializable {
    @Column(name = "image_id")
    Long imageId;

    @Column(name = "object_id")
    Long objectId;
}
