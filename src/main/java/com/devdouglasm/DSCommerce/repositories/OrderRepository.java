package com.devdouglasm.DSCommerce.repositories;

import com.devdouglasm.DSCommerce.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
