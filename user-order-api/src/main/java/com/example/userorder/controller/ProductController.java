package com.example.userorder.controller;

import com.example.userorder.dto.product.ProductCreateRequestDto;
import com.example.userorder.dto.product.ProductResponseDto;
import com.example.userorder.dto.product.ProductSearchCondition;
import com.example.userorder.dto.product.ProductUpdateRequestDto;
import com.example.userorder.service.ProductService;
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
    public ProductResponseDto createProduct(@RequestBody ProductCreateRequestDto request) {
        return productService.createProduct(request);
    }

    @GetMapping
    public Slice<ProductResponseDto> searchProducts(
            ProductSearchCondition condition,
            Pageable pageable
    ) {
        return productService.searchProducts(condition, pageable);
    }

    @GetMapping("/{productId}")
    public ProductResponseDto getProduct(@PathVariable Long productId) {
        return productService.getProductById(productId);
    }

    @PatchMapping("/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponseDto updateProduct(
            @PathVariable Long productId,
            @RequestBody ProductUpdateRequestDto request
    ) {
        return productService.updateProduct(productId, request);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
    }


}