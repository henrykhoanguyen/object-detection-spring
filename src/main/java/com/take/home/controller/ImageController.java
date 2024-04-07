package com.take.home.controller;

import com.drew.imaging.ImageProcessingException;
import com.take.home.model.Image;
import com.take.home.model.ImageRequest;
import com.take.home.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ImageController {
    @Autowired
    private final ImageService imageService;

    @GetMapping("/images")
    public ResponseEntity<List<Image>> getImages(@RequestParam("objects") String requestedObjects){
        List<Image> images;
        try {
            if (requestedObjects == null){
                images = imageService.getAllImages();
                return ResponseEntity.ok().body(images);
            }

            List<String> objects = Arrays.asList(requestedObjects.split(","));

            images = imageService.getImagesWithObjects(objects);
        } catch (NoSuchElementException ex){
            return new ResponseEntity(ex, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(images, HttpStatus.OK);
    }

    @GetMapping("/images/{imageId}")
    public ResponseEntity<Image> getImage(@PathVariable String imageId){
        Image image = null;
        try{
            image = imageService.getImage(Long.parseLong(imageId));
        } catch (NoSuchElementException ex){
            return new ResponseEntity(ex, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(image, HttpStatus.OK);
    }

    @PostMapping("/images")
    public ResponseEntity<Image> objectDetection(@RequestBody ImageRequest imageRequest) {
        Image image = null;
        try{
            image = imageService.getImageInfo(imageRequest);
        } catch (IOException e) {
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        } catch (ImageProcessingException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(image, HttpStatus.OK);
    }
}

