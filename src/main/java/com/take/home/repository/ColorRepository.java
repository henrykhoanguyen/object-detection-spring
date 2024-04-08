package com.take.home.repository;

import com.take.home.model.Color;
import com.take.home.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorRepository extends JpaRepository<Color, Long> {
}
