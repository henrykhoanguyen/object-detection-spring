package com.take.home.controller;

import com.take.home.repository.ImaggaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ImaggaController {

    private final ImaggaRepository repository;

    ImaggaController(ImaggaRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/images")
    public ResponseEntity getImages(){
        return ResponseEntity.ok().body(/* TODO */);
    }

    @GetMapping("/images")
    public ResponseEntity getImagesViaRequest(@RequestParam("objects") String objects){
        return ResponseEntity.ok().body(/* TODO */);
    }

    @GetMapping("/images/{imageId}")
    public ResponseEntity getImage(@PathVariable String imageId){
        return ResponseEntity.ok().body(/* TODO */);
    }

    @PostMapping("images")
    public ResponseEntity objectDetection(@RequestBody String requestBody){
        return ResponseEntity.ok().body(/* TODO */);
    }
}

