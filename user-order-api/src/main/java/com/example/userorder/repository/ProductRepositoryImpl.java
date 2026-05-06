package com.example.userorder.repository;

import com.example.userorder.dto.product.ProductSearchCondition;
import com.example.userorder.entity.Product;
import com.querydsl.core.BooleanBuilder;
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
    public Slice<Product> searchProducts(
            ProductSearchCondition condition,
            Pageable pageable
    ) {
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(product.id.eq(condition.id()));

        if(condition!=null && condition.name()!=null && !condition.name().isBlank()){
            builder.and(product.name.containsIgnoreCase(condition.name()));
        }

        List<Product> products = queryFactory
                .select(product)
                .where(builder)
                .orderBy(product.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()+1)
                .fetch();

        boolean hasNext = products.size() > pageable.getPageSize();
        if(hasNext){
            products.remove(pageable.getPageSize());
        }

        return new SliceImpl<>(products, pageable, hasNext);
    }
}
