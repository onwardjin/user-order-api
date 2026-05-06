package com.example.userorder.controller;

import com.example.userorder.dto.product.ProductCreateRequestDto;
import com.example.userorder.dto.product.ProductResponseDto;
import com.example.userorder.dto.product.ProductSearchCondition;
import com.example.userorder.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;


    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponseDto createProduct(
            @Valid @RequestBody ProductCreateRequestDto request
    ) {
        return productService.createProduct(request);
    }

    @GetMapping
    public Slice<ProductResponseDto> getProducts(
            ProductSearchCondition condition,
            Pageable pageable
    ) {
        return productService.getProducts(condition, pageable);
    }

    @GetMapping("/{productId}")
    public ProductResponseDto getProductById(
            @PathVariable Long productId
    ) {
        return productService.getProductById(productId);
    }
}