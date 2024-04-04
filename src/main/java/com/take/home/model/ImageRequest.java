package com.take.home.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ImageRequest {
    @NonNull
    private String imageUrl;
    private String label;
    private boolean enableObjectDetection;
}
