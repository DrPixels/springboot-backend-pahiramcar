package com.lindtsey.pahiramcar;

import com.github.javafaker.Faker;
import com.lindtsey.pahiramcar.customer.Customer;
import com.lindtsey.pahiramcar.customer.CustomerRepository;
import com.lindtsey.pahiramcar.employee.Employee;
import com.lindtsey.pahiramcar.employee.EmployeeRepository;
import com.lindtsey.pahiramcar.enums.MaritalStatus;
import com.lindtsey.pahiramcar.enums.Role;
import com.lindtsey.pahiramcar.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;

@SpringBootApplication
@EnableScheduling
public class PahiramcarApplication {

    public static void main(String[] args) {
        SpringApplication.run(PahiramcarApplication.class, args);
    }

    // Add a Super Admin Account
    @Bean
    public CommandLineRunner commandLineRunner(
            UserRepository userRepository
    ) {

        if (userRepository.existsByRole(Role.ADMIN)) {
            return args -> {};
        }

        return args -> {

            var employee = Employee.builder()
                    .role(Role.ADMIN)
                    .username("admin")
                    .password(new BCryptPasswordEncoder().encode("admin"))
                    .firstName("Admin")
                    .lastName("Admin")
                    .birthDate(LocalDate.now())
                    .mobilePhone("09097633529")
                    .email("superadmin@gmail.com")
                    .maritalStatus(MaritalStatus.SINGLE)
                    .nationality("Filipino")
                    .build();
            userRepository.save(employee);
        };

    }


}
