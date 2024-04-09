package com.take.home.repository;

import com.take.home.model.Object;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ObjectRepository extends JpaRepository<Object, Long> {
    Object findByName(String name);
}
