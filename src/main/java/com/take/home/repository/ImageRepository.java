package com.take.home.repository;

import com.take.home.model.Image;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ImageRepository extends MongoRepository<Image, Long> {
    // Return all image metadata in DB
    List<Image> findAll();

    // Return metadata in DB from request imageId
//    Image findById(Long imageId);

}
