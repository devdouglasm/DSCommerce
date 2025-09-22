package com.devdouglasm.DSCommerce.controllers;

import com.devdouglasm.DSCommerce.dto.OrderDTO;
import com.devdouglasm.DSCommerce.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    @Autowired
    private OrderService service;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderDTO> findById(@PathVariable Long id) {
            return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_CLIENT')")
    @PostMapping
    public ResponseEntity<OrderDTO> insert(@Valid @RequestBody OrderDTO dto) { // body that came in json is the product dto
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

}
