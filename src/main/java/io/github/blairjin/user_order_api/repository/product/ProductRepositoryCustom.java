package io.github.blairjin.user_order_api.repository.product;

import io.github.blairjin.user_order_api.domain.product.Product;
import io.github.blairjin.user_order_api.dto.product.SearchProductCondition;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ProductRepositoryCustom {
    public Slice<Product> search(SearchProductCondition condition, Pageable pageable);
}