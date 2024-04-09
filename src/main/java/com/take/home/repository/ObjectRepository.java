package com.take.home.repository;

import com.take.home.model.Image;
import com.take.home.model.Object;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface ObjectRepository extends JpaRepository<Object, Long> {
    Object findByName(String name);

    @Query("select i.image_url from object o, image_objects io, image i where o.name like \"?1\" and o.object_id = io.object_id and io.image_id = i.image_id")
    List<String> findImagesByName(String name);
}
