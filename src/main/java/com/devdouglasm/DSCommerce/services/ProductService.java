package com.devdouglasm.DSCommerce.services;

import com.devdouglasm.DSCommerce.dto.ProductDTO;
import com.devdouglasm.DSCommerce.entities.Product;
import com.devdouglasm.DSCommerce.repositories.ProductRepository;
import com.devdouglasm.DSCommerce.services.exception.DatabaseException;
import com.devdouglasm.DSCommerce.services.exception.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Transactional(readOnly = true) // when the method just show anything from db, and doesn't change anything
    public ProductDTO findById(Long id) {   // findById method already has orElseThrow exception
        Product product = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
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

        try {
            // instantiates a new product just with id reference, the variable doesn't go in db
            Product product = repository.getReferenceById(id);
            copyDtoToEntity(dto, product);
            // save the new product in db
            product = repository.save(product);
            // make the product dto again, and return it
            return new ProductDTO(product);
        }
        catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Resource not found");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS) // don't execute transactional mode if it calls alone
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Resource not found");
        }
        try {
            repository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Referencial violation");
        }
    }

    private void copyDtoToEntity(ProductDTO dto, Product entity) {
        // copy the attributes from received dto and set to new product
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setImgUrl(dto.getImgUrl());
    }
}
