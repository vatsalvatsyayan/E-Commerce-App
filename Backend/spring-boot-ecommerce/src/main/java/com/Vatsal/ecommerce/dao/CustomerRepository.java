package com.Vatsal.ecommerce.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Vatsal.ecommerce.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
