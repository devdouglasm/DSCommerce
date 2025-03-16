package com.devdouglasm.DCCommerce.repositories;

import com.devdouglasm.DCCommerce.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
