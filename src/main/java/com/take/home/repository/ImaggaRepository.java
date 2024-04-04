package com.take.home.repository;

import com.take.home.model.Image;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ImaggaRepository extends MongoRepository<Image, Long> {
    // Return all image metadata in DB
    Image[] findAllMetadata();

    // Return image URLs from DB with requested objects
    Image findImagesByObjects(String[] objects);

    // Return metadata in DB from request imageId
    Image findById(String imageId);

    // Return image data, its label, its identifier provided by the persistent data store, any objects detected

}
