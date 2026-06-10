package io.github.blairjin.user_order_api.application.cart.service;

import io.github.blairjin.user_order_api.application.cart.reader.CartReader;
import io.github.blairjin.user_order_api.application.product.reader.ProductReader;
import io.github.blairjin.user_order_api.domain.cart.Cart;
import io.github.blairjin.user_order_api.domain.cart.CartItem;
import io.github.blairjin.user_order_api.domain.product.Product;
import io.github.blairjin.user_order_api.dto.cart.CartItemResponse;
import io.github.blairjin.user_order_api.exception.NOT_FOUND.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartQueryService {
    private final CartReader cartReader;
    private final ProductReader productReader;

    public List<CartItemResponse> get(Long userId){
        Long cartId = cartReader.getCartIdByUserId(userId);
        List<CartItem> items = cartReader.getItemsByCartId(cartId);
        List<Long> productIds = items.stream().map(CartItem::getProductId).toList();

        Map<Long, Product> productMap = getProductMap(productIds);

        List<CartItemResponse> result = new ArrayList<>();

        for(CartItem ci : items){
            Product product = productMap.get(ci.getProductId());

            if(product==null){
                throw new ProductNotFoundException();
            }

            result.add(CartItemResponse.from(product, ci));
        }

        return result;
    }

    private Map<Long, Product> getProductMap(List<Long> productIds){
        return productReader.getAllByIds(productIds).stream()
                .collect(Collectors.toMap(Product::getId, product -> product));
    }
}
