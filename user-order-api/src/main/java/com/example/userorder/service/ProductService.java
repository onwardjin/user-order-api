package com.example.userorder.service;

import com.example.userorder.dto.product.ProductCreateRequestDto;
import com.example.userorder.dto.product.ProductResponseDto;
import com.example.userorder.dto.product.ProductSearchCondition;
import com.example.userorder.entity.Product;
import com.example.userorder.exception.ProductNotFoundException;
import com.example.userorder.repository.OrderRepository;
import com.example.userorder.repository.ProductRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public ProductService(ProductRepository productRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public ProductResponseDto createProduct(ProductCreateRequestDto request) {
        if (productRepository.existsByName(request.name())) {
            throw new IllegalArgumentException("중복된 상품입니다.");
        }

        Product product = Product.createProduct(request.name(), request.price(), request.stockQuantity());
        Product savedProduct = productRepository.save(product);

        return ProductResponseDto.from(savedProduct);
    }

    public Slice<ProductResponseDto> getProducts(ProductSearchCondition condition, Pageable pageable) {
        return productRepository.searchProducts(condition, pageable)
                .map(ProductResponseDto::from);
    }

    public ProductResponseDto getProductById(Long productId) {
        return productRepository.findById(productId)
                .map(ProductResponseDto::from)
                .orElseThrow(ProductNotFoundException::new);
    }
}