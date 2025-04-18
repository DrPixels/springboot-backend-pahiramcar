package com.lindtsey.pahiramcar.bookingproofimages;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingProofImageRepository extends JpaRepository<BookingProofImage, Integer> {
}
