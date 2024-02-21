package com.Vatsal.ecommerce.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Vatsal.ecommerce.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    
}
