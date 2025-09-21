package com.devdouglasm.DSCommerce.services;

import com.devdouglasm.DSCommerce.dto.OrderDTO;
import com.devdouglasm.DSCommerce.dto.OrderItemDTO;
import com.devdouglasm.DSCommerce.entities.*;
import com.devdouglasm.DSCommerce.repositories.OrderItemRepository;
import com.devdouglasm.DSCommerce.repositories.OrderRepository;
import com.devdouglasm.DSCommerce.repositories.ProductRepository;
import com.devdouglasm.DSCommerce.repositories.UserRepository;
import com.devdouglasm.DSCommerce.services.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Transactional(readOnly = true)
    public OrderDTO findById(Long id) {
        Order order = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
        return new OrderDTO(order);
    }

    @Transactional
    public OrderDTO insert(OrderDTO dto) {

        Order order = new Order();

        order.setStatus(OrderStatus.WAITING_PAYMENT);
        order.setMoment(Instant.now());

        User user = userService.authenticated();
        order.setClient(user);

        for (OrderItemDTO item : dto.getItems()) {
             Product product = productRepository.getReferenceById(item.getProductId());
             OrderItem orderItem = new OrderItem(order, product, item.getQuantity(), item.getPrice());
             order.getItems().add(orderItem);
        }

        repository.save(order);
        orderItemRepository.saveAll(order.getItems());

        return new OrderDTO(order);

    }
}
