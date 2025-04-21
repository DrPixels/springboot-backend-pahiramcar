package com.lindtsey.pahiramcar;

import com.github.javafaker.Faker;
import com.lindtsey.pahiramcar.customer.Customer;
import com.lindtsey.pahiramcar.customer.CustomerRepository;
import com.lindtsey.pahiramcar.employee.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PahiramcarApplication {

    public static void main(String[] args) {
        SpringApplication.run(PahiramcarApplication.class, args);
    }

//     Add a default employee (super admin)
//    @Bean
//    public CommandLineRunner commandLineRunner(
//            EmployeeRepository employeeRepository
//    ) {
//        return args -> {
//
//            for (int i = 0; i < 5; i++) {
//                Faker faker = new Faker();
//
//                var customer = Employee.builder()
//                        .firstName(faker.name().firstName())
//                        .middleName(faker.name().firstName())
//                        .lastName(faker.name().lastName())
//                        .birthDate(faker.date().between())
//                        .mobilePhone(faker.phoneNumber())
//                        .email("giousalvador" + i + "@gmail.com")
//                        .build();
//            }
//
//        }
//
//    }


}
