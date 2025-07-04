package com.lindtsey.pahiramcar.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Optional<Customer> findCustomerByUsername(String username);

    int countCustomerByCreatedAtBefore(LocalDateTime startDateTime);

    int countCustomersByCreatedAtBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);
}
