package com.take.home.repository;

import com.take.home.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
//    List<Image> findAll();
}
