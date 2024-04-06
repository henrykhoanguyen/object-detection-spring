package com.take.home.repository;

import com.take.home.model.Image;
import com.take.home.model.Object;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ObjectRepository extends MongoRepository<Object, Long> {

    Object findByName(String object);
}
