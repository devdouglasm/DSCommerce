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
        copyDtoToEntity(dto, product);
        // save the new product in db
        product = repository.save(product);
        // make the product dto again, and return it
        return new ProductDTO(product);
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO dto) {

        // instantiates a new product just with id reference, the variable doesn't go in db
        Product product = repository.getReferenceById(id);
        copyDtoToEntity(dto, product);
        // save the new product in db
        product = repository.save(product);
        // make the product dto again, and return it
        return new ProductDTO(product);
    }

    private void copyDtoToEntity(ProductDTO dto, Product entity) {
        // copy the attributes from received dto and set to new product
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setImgUrl(dto.getImgUrl());
    }
}
