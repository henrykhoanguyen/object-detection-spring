package com.take.home.repository;

import com.take.home.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    // Return all image metadata in DB
//    List<Image> findAll();

    // Return metadata in DB from request imageId
//    Image findById(Long imageId);

}
