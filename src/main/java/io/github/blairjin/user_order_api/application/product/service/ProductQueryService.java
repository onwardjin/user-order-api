package io.github.blairjin.user_order_api.application.product.service;

import io.github.blairjin.user_order_api.application.product.reader.ProductReader;
import io.github.blairjin.user_order_api.application.user.reader.UserReader;
import io.github.blairjin.user_order_api.domain.product.Product;
import io.github.blairjin.user_order_api.domain.user.vo.UserName;
import io.github.blairjin.user_order_api.dto.product.ProductResponse;
import io.github.blairjin.user_order_api.dto.product.SearchProductCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductQueryService {
    private final ProductReader productReader;
    private final UserReader userReader;

    public Slice<ProductResponse> search(SearchProductCondition condition, Pageable pageable){
        Slice<Product> products = productReader.search(condition, pageable);
        List<Long> userIds = products.stream().map(Product::getUserId).toList();

        Map<Long, String> userNameMap = userReader.getUserNameByIds(userIds);

        return products.map(product -> {
            UserName userName = UserName.of(userNameMap.get(product.getUserId()));
            return ProductResponse.from(product, userName);
        });
    }

    public ProductResponse get(Long productId){
        Product product = productReader.getProductById(productId);
        UserName userName = userReader.getUserNameById(product.getUserId());

        return ProductResponse.from(product, userName);
    }
}