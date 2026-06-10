package io.github.blairjin.user_order_api.repository.cart;

import io.github.blairjin.user_order_api.domain.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("SELECT c.id FROM Cart c WHERE c.userId = :userId")
    Optional<Long> findIdByUserId(Long userId);

    Optional<Cart> findByUserId(Long userId);
}