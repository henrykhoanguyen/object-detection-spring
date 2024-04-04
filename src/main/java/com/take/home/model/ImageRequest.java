package com.take.home.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ImageRequest {
    @NonNull
    private String imageUrl;
    private String label;
    private boolean enableObjectDetection;
}
