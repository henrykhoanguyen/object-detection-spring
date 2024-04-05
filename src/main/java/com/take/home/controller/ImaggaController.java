package com.take.home.controller;

import com.drew.imaging.ImageProcessingException;
import com.take.home.model.Image;
import com.take.home.model.ImageRequest;
import com.take.home.repository.ImaggaRepository;
import com.take.home.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ImaggaController {
    @Autowired
    private final ImageService imageService;
    @Autowired
    private final ImaggaRepository repository;

    ImaggaController(ImageService imageService, ImaggaRepository repository) {
        this.imageService = imageService;
        this.repository = repository;
    }

    @GetMapping("/images")
    public ResponseEntity<List<Image>> getImages(){
        List<Image> images = imageService.getAllImages();
        return ResponseEntity.ok().body(images);
    }

    @GetMapping("/images")
    public ResponseEntity getImagesViaRequest(@RequestParam("objects") String requestedObjects){
        String[] objects = requestedObjects.split(",");
        List<Image> images = imageService.getImagesWithObjects(objects);
        return ResponseEntity.ok().body(images);
    }

    @GetMapping("/images/{imageId}")
    public ResponseEntity getImage(@PathVariable String imageId){
        Image image = imageService.getImage(imageId);
        return ResponseEntity.ok().body(image);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("images")
    public ResponseEntity objectDetection(@RequestBody ImageRequest imageRequest) throws ImageProcessingException, IOException {
//        imageService.extractMetadataFromImageUrl(imageRequest);
        return ResponseEntity.ok().body(imageService.extractMetadataFromImageUrl(imageRequest.getImageUrl()));
    }
}

