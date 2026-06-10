package io.github.blairjin.user_order_api.repository.product;

import io.github.blairjin.user_order_api.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {
    Optional<Product> findByUserIdAndId(Long userId, Long id);

    @Query("SELECT p.stockQuantity FROM Product p where p.id = :productId")
    Optional<Integer> findStockQuantityById(Long productId);
}