package com.devdouglasm.DCCommerce.services;

import com.devdouglasm.DCCommerce.dto.ProductDTO;
import com.devdouglasm.DCCommerce.entities.Product;
import com.devdouglasm.DCCommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable) { // pageable to return the products pageable
        Page<Product> products = repository.findAll(pageable);
        return products.map(x -> new ProductDTO(x)); // Page's already a stream in java
    }

    @Transactional
    public ProductDTO insert(ProductDTO dto) {

        // instantiates a new product to save in db
        Product product = new Product();
        // copy the attributes from received dto and set to new product
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setImgUrl(dto.getImgUrl());

        // save the new product in db
        product = repository.save(product);
        // make the product dto again, and return it
        return new ProductDTO(product);

    }
}
