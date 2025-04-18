package com.lindtsey.pahiramcar.carimages;

public record CarImageDTO(String imageName,
        String imageUrl,
        String cloudinaryImageId,
        Integer carId) {
}
