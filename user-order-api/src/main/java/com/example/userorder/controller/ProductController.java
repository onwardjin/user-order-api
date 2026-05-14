package com.example.userorder.controller;

import com.example.userorder.dto.product.ProductCreateRequest;
import com.example.userorder.dto.product.ProductResponse;
import com.example.userorder.dto.product.ProductUpdateRequest;
import com.example.userorder.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long createProduct(
            @Valid @RequestBody ProductCreateRequest request
    ) {
        return productService.createProduct(request);
    }

    @GetMapping("/{productId}")
    public ProductResponse getProduct(@PathVariable Long productId) {
        return productService.getProduct(productId);
    }

    @PatchMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProduct(
            @PathVariable Long productId,
            @RequestBody ProductUpdateRequest request
    ) {
        productService.updateProduct(productId, request);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
    }
}