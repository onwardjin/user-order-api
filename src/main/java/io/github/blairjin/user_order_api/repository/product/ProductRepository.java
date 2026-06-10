package io.github.blairjin.user_order_api.repository.product;

import io.github.blairjin.user_order_api.domain.product.Product;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {
    Optional<Product> findByUserIdAndId(Long userId, Long id);

    @Query("SELECT p.stockQuantity FROM Product p WHERE p.id = :productId")
    Optional<Integer> findStockQuantityById(Long productId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Product p WHERE p.id = :productId")
    Optional<Product> findByIdWithLock(Long productId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Product> findAllByIdIn(List<Long> productIds);
}