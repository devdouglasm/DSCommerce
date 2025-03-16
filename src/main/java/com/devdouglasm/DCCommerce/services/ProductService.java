package com.devdouglasm.DCCommerce.services;

import com.devdouglasm.DCCommerce.dto.ProductDTO;
import com.devdouglasm.DCCommerce.entities.Product;
import com.devdouglasm.DCCommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Transactional(readOnly = true) // when the method just show anything from db, and doesn't change anything
    public ProductDTO findById(Long id) {
        Optional<Product> result = repository.findById(id);
        Product product = result.get();
        return new ProductDTO(product);
    }

}
