package com.take.home.controller;

import com.take.home.exception.ResourceNotFoundException;
import com.take.home.model.Image;
import com.take.home.model.ImageRequest;
import com.take.home.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ImageController {
    @Autowired
    private final ImageService imageService;

    @GetMapping("/images")
    public ResponseEntity<List<Image>> getAllImages(){
        List<Image> images = new ArrayList<>();
        try{
            images = imageService.getAllImages();
        }catch (ResourceNotFoundException ex){
            return new ResponseEntity(ex, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(images, HttpStatus.OK);
    }

    @GetMapping(value = "/images", params = "objects")
    public ResponseEntity<List<Image>> getImagesWithObjects(@RequestParam("objects") String requestedObjects){
        List<Image> images;
        try {
            List<String> objects = Arrays.asList(requestedObjects.split(","));

            images = imageService.getImagesWithObjects(objects);
        } catch (ResourceNotFoundException ex){
            return new ResponseEntity(ex, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(images, HttpStatus.OK);
    }

    @GetMapping("/images/{imageId}")
    public ResponseEntity<Image> getImage(@PathVariable String imageId){
        Image image = null;
        try{
            image = imageService.getImage(Long.parseLong(imageId));
        } catch (ResourceNotFoundException ex){
            return new ResponseEntity(ex, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(image, HttpStatus.OK);
    }

    @PostMapping("/images")
    public ResponseEntity<Image> objectDetection(@RequestBody ImageRequest imageRequest) {
        Image image = null;
        try{
            image = imageService.getImageInfo(imageRequest);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(image, HttpStatus.OK);
    }
}

