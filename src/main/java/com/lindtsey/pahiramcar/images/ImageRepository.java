package com.lindtsey.pahiramcar.images;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {

    @Modifying
    @Query("DELETE FROM Image i WHERE i.imageId = :imageId")
    void deleteImageByImageId(@Param("imageId") Integer imageId);
}
