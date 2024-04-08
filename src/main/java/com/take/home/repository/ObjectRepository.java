package com.take.home.repository;

import com.take.home.model.Object;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ObjectRepository extends JpaRepository<Object, Long> {

    Object findByName(String name);
}
