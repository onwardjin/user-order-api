package io.github.blairjin.user_order_api.application.cart.reader;

import io.github.blairjin.user_order_api.domain.cart.Cart;
import io.github.blairjin.user_order_api.domain.cart.CartItem;
import io.github.blairjin.user_order_api.exception.NOT_FOUND.CartItemNotFoundException;
import io.github.blairjin.user_order_api.exception.NOT_FOUND.CartNotFoundException;
import io.github.blairjin.user_order_api.repository.cart.CartItemRepository;
import io.github.blairjin.user_order_api.repository.cart.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartReader {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public Cart getCartByUserId(Long userId){
        return cartRepository.findByUserId(userId)
                .orElseThrow(CartNotFoundException::new);
    }

    public Long getCartIdByUserId(Long userId){
        return cartRepository.findIdByUserId(userId)
                .orElseThrow(CartNotFoundException::new);
    }

    public List<CartItem> getItemsByCartId(Long cartId){
        return cartItemRepository.findAllByCartId(cartId);
    }

    public CartItem getItemByCartIdAndId(Long cartId, Long cartItemId){
        return cartItemRepository.findByCartIdAndId(cartId, cartItemId)
                .orElseThrow(CartItemNotFoundException::new);
    }
}