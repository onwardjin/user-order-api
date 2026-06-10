package io.github.blairjin.user_order_api.application.product.usecase;

import io.github.blairjin.user_order_api.application.product.service.ProductQueryService;
import io.github.blairjin.user_order_api.dto.product.ProductResponse;
import io.github.blairjin.user_order_api.dto.product.SearchProductCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchProductUseCase {
    private final ProductQueryService productQueryService;

    public Slice<ProductResponse> execute(SearchProductCondition condition, Pageable pageable){
        return productQueryService.search(condition, pageable);
    }
}