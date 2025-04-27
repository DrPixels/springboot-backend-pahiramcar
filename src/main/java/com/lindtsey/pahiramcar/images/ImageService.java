package com.lindtsey.pahiramcar.images;

import com.lindtsey.pahiramcar.bookings.Booking;
import com.lindtsey.pahiramcar.bookings.BookingRepository;
import com.lindtsey.pahiramcar.car.Car;
import com.lindtsey.pahiramcar.car.CarRepository;
import com.lindtsey.pahiramcar.cloudinary.CloudinaryService;
import com.lindtsey.pahiramcar.customer.Customer;
import com.lindtsey.pahiramcar.customer.CustomerRepository;
import com.lindtsey.pahiramcar.employee.Employee;
import com.lindtsey.pahiramcar.employee.EmployeeRepository;
import com.lindtsey.pahiramcar.enums.ImageOwnerType;
import com.lindtsey.pahiramcar.transactions.TransactionRepository;
import com.lindtsey.pahiramcar.transactions.childClass.damageRepairFee.DamageRepairFeeTransaction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final CloudinaryService cloudinaryService;
    private final CarRepository carRepository;
    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final TransactionRepository transactionRepository;

    public ImageService(ImageRepository imageRepository,
                        CloudinaryService cloudinaryService,
                        CarRepository carRepository,
                        BookingRepository bookingRepository,
                        CustomerRepository customerRepository,
                        EmployeeRepository employeeRepository, TransactionRepository transactionRepository) {
        this.imageRepository = imageRepository;
        this.cloudinaryService = cloudinaryService;
        this.carRepository = carRepository;
        this.bookingRepository = bookingRepository;
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
        this.transactionRepository = transactionRepository;
    }

    public List<Image> saveImages(MultipartFile[] multipartFiles, ImageOwnerType imageOwnerType, Integer ownerId) throws IOException {

        List<Image> images = new ArrayList<>();
        System.out.println("LENGTHHH: " + multipartFiles.length);
        for (MultipartFile file: multipartFiles) {
            //Uploading the image in the Cloudinary
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            if( bufferedImage == null ) {
                throw new IOException("Failed to read the image.");
            }

            Map result = cloudinaryService.upload(file);
            String imageName = (String) result.get("original_filename");
            String imageUrl = (String) result.get("url");
            String imageCloudinaryId = (String) result.get("public_id");

            Image image = new Image(imageName, imageUrl, imageCloudinaryId);

            switch (imageOwnerType) {
                case CAR:
                    Car car = carRepository.findById(ownerId).orElseThrow(() -> new RuntimeException("Car not found"));
                    image.setCar(car);
                    break;
                case BOOKING:
                    Booking booking = bookingRepository.findById(ownerId).orElseThrow(() -> new RuntimeException("Booking not found"));
                    image.setBooking(booking);
                    break;
                case CUSTOMER:
                    Customer customer = customerRepository.findById(ownerId).orElseThrow(() -> new RuntimeException("Customer not found"));
                    image.setCustomer(customer);
                    break;
                case EMPLOYEE:
                    Employee employee = employeeRepository.findById(ownerId).orElseThrow(() -> new RuntimeException("Employee not found"));
                    image.setEmployee(employee);
                    break;
                case TRANSACTION:
                    DamageRepairFeeTransaction transaction = (DamageRepairFeeTransaction) transactionRepository.findById(ownerId).orElseThrow(() -> new RuntimeException("Transaction not found"));
                    image.setDamageRepairFeeTransaction(transaction);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown owner type");
            }

            Image savedImaged = imageRepository.save(image);
            images.add(savedImaged);

        }
        return images;
    }

    @Transactional
    public void deleteImage(Integer imageId) throws IOException {
        // Get from the database
        Image image = imageRepository.findById(imageId).orElseThrow(() -> new RuntimeException("Image not found"));

        System.out.println("IAMGE ID TO BE DELETED: " + image.getImageId());
        // Delete from the database
        imageRepository.deleteImageByImageId(imageId);

        // Delete from the cloudinary
        cloudinaryService.delete(image.getPublicId());
    }
}
