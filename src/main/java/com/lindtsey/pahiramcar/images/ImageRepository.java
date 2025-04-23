package com.lindtsey.pahiramcar.images;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {

    void deleteImageByImageId(Integer imageId);
}
