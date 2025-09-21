package com.devdouglasm.DSCommerce.repositories;

import com.devdouglasm.DSCommerce.entities.OrderItem;
import com.devdouglasm.DSCommerce.entities.OrderItemPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {
}
