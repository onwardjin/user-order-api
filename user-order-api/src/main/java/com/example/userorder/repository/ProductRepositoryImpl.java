package com.example.userorder.repository;

import com.example.userorder.dto.product.ProductSearchCondition;
import com.example.userorder.entity.Product;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;

import static com.example.userorder.entity.QProduct.product;

public class ProductRepositoryImpl implements ProductRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public ProductRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Slice<Product> searchProducts(ProductSearchCondition condition, Pageable pageable) {
        List<Product> products = queryFactory
                .selectFrom(product)
                .where(
                        productNameContains(condition.name()),
                        priceGoe(condition.minPrice()),
                        priceLoe(condition.maxPrice())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = products.size() > pageable.getPageSize();
        if (hasNext) {
            products.remove(pageable.getPageSize());
        }

        return new SliceImpl<>(products, pageable, hasNext);
    }

    private BooleanExpression productNameContains(String productName) {
        if (productName == null || productName.isBlank()) {
            return null;
        }
        return product.name.containsIgnoreCase(productName);
    }

    private BooleanExpression priceGoe(Integer price) {
        if (price == null) {
            return null;
        }
        return product.unitPrice.goe(price);
    }

    private BooleanExpression priceLoe(Integer price) {
        if (price == null) {
            return null;
        }
        return product.unitPrice.loe(price);
    }
}