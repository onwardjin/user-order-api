package com.example.userorder.service;

import com.example.userorder.dto.product.ProductCreateRequestDto;
import com.example.userorder.dto.product.ProductResponseDto;
import com.example.userorder.dto.product.ProductSearchCondition;
import com.example.userorder.dto.product.ProductUpdateRequestDto;
import com.example.userorder.entity.Product;
import com.example.userorder.exception.ProductNotFoundException;
import com.example.userorder.repository.ProductRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;


    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductResponseDto createProduct(
            ProductCreateRequestDto request
    ) {
        Product product = Product.createProduct(
                request.name(),
                request.unitPrice(),
                request.stockQuantity()
        );

        Product savedProduct = productRepository.save(product);
        return ProductResponseDto.from(savedProduct);
    }

    public Slice<ProductResponseDto> searchProducts(
            ProductSearchCondition condition,
            Pageable pageable
    ) {
        return productRepository.searchProducts(condition, pageable)
                .map(ProductResponseDto::from);
    }

    public ProductResponseDto getProductById(Long productId) {
        return productRepository.findById(productId)
                .map(ProductResponseDto::from)
                .orElseThrow(ProductNotFoundException::new);
    }

    @Transactional
    public ProductResponseDto updateProduct(
            Long productId,
            ProductUpdateRequestDto request
    ) {
        Product product = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);

        product.updateProduct(request.name(), request.unitPrice(), request.stockQuantity());
        return ProductResponseDto.from(product);
    }

    @Transactional
    public void deleteProduct(Long productId) {
        int deletedCount = productRepository.deleteByProductId(productId);

        if (deletedCount == 0) {
            throw new ProductNotFoundException();
        }
    }
}