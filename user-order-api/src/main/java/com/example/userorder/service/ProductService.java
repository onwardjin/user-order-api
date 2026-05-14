package com.example.userorder.service;

import com.example.userorder.common.exception.ProductNotFoundException;
import com.example.userorder.domain.common.vo.Money;
import com.example.userorder.domain.product.Product;
import com.example.userorder.domain.product.vo.ProductName;
import com.example.userorder.domain.product.vo.StockQuantity;
import com.example.userorder.dto.product.ProductCreateRequest;
import com.example.userorder.dto.product.ProductResponse;
import com.example.userorder.dto.product.ProductUpdateRequest;
import com.example.userorder.repository.ProductRepository;
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
    public Long createProduct(ProductCreateRequest request) {
        Product product = Product.createProduct(
                ProductName.of(request.name()),
                Money.of(request.unitPrice()),
                StockQuantity.of(request.stockQuantity())
        );

        return productRepository.save(product).getId();
    }

    public ProductResponse getProduct(Long productId) {
        return productRepository.findById(productId)
                .map(ProductResponse::from)
                .orElseThrow(ProductNotFoundException::new);
    }

    @Transactional
    public void updateProduct(Long productId, ProductUpdateRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);

        if (request.name() != null) {
            product.updateName(ProductName.of(request.name()));
        }

        if (request.unitPrice() != null) {
            product.updateUnitPrice(Money.of(request.unitPrice()));
        }

        if (request.stockQuantity() != null) {
            product.updateStockQuantity(StockQuantity.of(request.stockQuantity()));
        }
    }

    @Transactional
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);

        productRepository.delete(product);
    }
}