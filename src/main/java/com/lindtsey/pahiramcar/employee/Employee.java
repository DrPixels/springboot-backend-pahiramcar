package com.lindtsey.pahiramcar.employee;

import com.lindtsey.pahiramcar.enums.AdminRoles;
import com.lindtsey.pahiramcar.transactions.Transaction;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer employeeId;

    @Column(nullable = false)
    private AdminRoles role;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column
    private String employeeImageURL;

    @NotBlank(message = "First name is required.")
    @Column(nullable = false)
    private String firstName;

    @Column
    private String middleName;

    @NotBlank(message = "Last name is required.")
    @Column(nullable = false)
    private String lastName;

    @NotNull(message = "Birthdate is required.")
    @Column(nullable = false)
    private LocalDate birthDate;

    @Pattern(regexp = "^09\\d{9}$", message = "Phone number must start with 09 and must be 11 digits long.")
    @Column(unique = true)
    private String mobilePhone;

    @Pattern(regexp= "^[a-z][a-z0-9]*@[a-z]+\\.com$", message = "Invalid Email Format")
    @Column(unique = true)
    private String email;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(
            mappedBy = "employee"
    )
    private List<Transaction> transactions;

}
