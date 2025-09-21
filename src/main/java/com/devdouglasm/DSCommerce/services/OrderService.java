package com.devdouglasm.DSCommerce.services;

import com.devdouglasm.DSCommerce.dto.OrderDTO;
import com.devdouglasm.DSCommerce.entities.Order;
import com.devdouglasm.DSCommerce.repositories.OrderRepository;
import com.devdouglasm.DSCommerce.services.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Transactional(readOnly = true)
    public OrderDTO findById(Long id) {
        Order order = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
        return new OrderDTO(order);
    }
}
