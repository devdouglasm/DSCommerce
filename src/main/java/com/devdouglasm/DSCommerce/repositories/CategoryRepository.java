package com.devdouglasm.DSCommerce.repositories;

import com.devdouglasm.DSCommerce.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
