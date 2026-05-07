package com.example.userorder.repository;

import com.example.userorder.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {
    @Modifying
    @Query("DELETE FROM Product p WHERE p.id = :productId")
    int deleteByProductId(@Param("productId") Long productId);
}