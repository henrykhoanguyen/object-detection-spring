package com.take.home.repository;

import com.take.home.model.Image;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ImaggaRepository extends MongoRepository {
    Image findById(String imageId);

}
