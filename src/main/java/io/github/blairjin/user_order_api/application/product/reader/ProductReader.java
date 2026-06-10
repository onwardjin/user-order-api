package io.github.blairjin.user_order_api.application.product.reader;

import io.github.blairjin.user_order_api.domain.product.Product;
import io.github.blairjin.user_order_api.dto.product.SearchProductCondition;
import io.github.blairjin.user_order_api.exception.NOT_FOUND.ProductNotFoundException;
import io.github.blairjin.user_order_api.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductReader {
    private final ProductRepository productRepository;

    public Integer getStockById(Long productId){
        return productRepository.findStockQuantityById(productId)
                .orElseThrow(ProductNotFoundException::new);
    }

    public Product getProductById(Long productId){
        return productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);
    }

    public Product getProductByUserIdAndId(Long userId, Long productId){
        return productRepository.findByUserIdAndId(userId, productId)
                .orElseThrow(ProductNotFoundException::new);
    }

    public List<Product> getAllByIds(List<Long> productIds){
        return productRepository.findAllById(productIds);
    }

    public Slice<Product> search(SearchProductCondition condition, Pageable pageable){
        return productRepository.search(condition, pageable);
    }
}