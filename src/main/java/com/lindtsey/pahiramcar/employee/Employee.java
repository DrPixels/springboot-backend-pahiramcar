package com.lindtsey.pahiramcar.employee;

import com.lindtsey.pahiramcar.enums.AdminRoles;
import com.lindtsey.pahiramcar.transactions.Transaction;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
import java.util.List;

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

    @OneToMany(
            mappedBy = "employee"
    )
    private List<Transaction> transactions;

    public Employee() {};

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public AdminRoles getRole() {
        return role;
    }

    public void setRole(AdminRoles role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
