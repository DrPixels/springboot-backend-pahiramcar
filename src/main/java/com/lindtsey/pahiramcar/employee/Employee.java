package com.lindtsey.pahiramcar.employee;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.lindtsey.pahiramcar.images.Image;
import com.lindtsey.pahiramcar.transactions.Transaction;
import com.lindtsey.pahiramcar.user.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@SuperBuilder
@Table(name = "Employees")
@NoArgsConstructor
@AllArgsConstructor
public class Employee extends User {

    @OneToOne(
            mappedBy = "employee",
            cascade = CascadeType.ALL
    )
    private Image employeeImage;

    @OneToMany(
            mappedBy = "employee"
    )
    @JsonManagedReference
    private List<Transaction> transactions;

}
