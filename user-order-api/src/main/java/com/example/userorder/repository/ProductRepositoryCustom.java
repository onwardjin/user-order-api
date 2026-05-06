package com.example.userorder.repository;

import com.example.userorder.dto.product.ProductSearchCondition;
import com.example.userorder.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ProductRepositoryCustom {
    Slice<Product> searchProducts(
            ProductSearchCondition condition,
            Pageable pageable
    );
}
