package com.lindtsey.pahiramcar.cloudinary;

import com.lindtsey.pahiramcar.car.Car;
import com.lindtsey.pahiramcar.carimages.CarImage;
import com.lindtsey.pahiramcar.carimages.CarImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class CloudinaryController {

    private final CloudinaryService cloudinaryService;
    private final CarImageService carImageService;

    public CloudinaryController(CloudinaryService cloudinaryService, CarImageService carImageService) {
        this.cloudinaryService = cloudinaryService;
        this.carImageService = carImageService;
    }

    @GetMapping("/list/{car-id}/images")
    public ResponseEntity<?> findAllByCarId(@PathVariable("car-id") Integer carId) {
        List<CarImage> carImageList = carImageService.findAllCarImageByCarId(carId);
        return ResponseEntity.status(HttpStatus.OK).body(carImageList);
    }

//    @PostMapping("/api/cloudinary/upload")
//    public Map uploadImage(@PathVariable("car-id") Integer carId, @RequestParam MultipartFile multipartFile) throws IOException {
//        BufferedImage bufferedImage = ImageIO.read(multipartFile.getInputStream());
//        if( bufferedImage == null ) {
//            throw new IOException("Failed to read the image from the file");
//        }
//        Map result = cloudinaryService.upload(multipartFile);
//
//        // Getting the fields for the car image
//        String carImageName = (String) result.get("original_filename");
//        String carImageUrl = (String) result.get("url");
//        String carImageCloudinaryId = (String) result.get("public_id");
//
//        CarImage image = new CarImage(carImageName, carImageUrl, carImageCloudinaryId);
//
//        Car car = new Car();
//        car.setCarId(carId);
//
//        image.setCar(car);
//
//        carImageService.save(image);
//        return result;
//    }

    @DeleteMapping("/delete/{car-image-id}")
    public ResponseEntity<?> deleteCarImageById(@PathVariable("car-image-id") Integer carImageId) {

        Optional<CarImage> carImageOptional = carImageService.findCarImageById(carImageId);

        if(carImageOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Car image not found.");
        }

        CarImage carImage = carImageOptional.get();
        String carImageCloudinaryId = carImage.getCloudinaryImageId();

        try {
            cloudinaryService.delete(carImageCloudinaryId);
        } catch(IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

        carImageService.deleteCarImageById(carImageId);

        return ResponseEntity.status(HttpStatus.OK).body("Car Image has been successfully deleted.");
    }

}
