package io.github.blairjin.user_order_api.domain.cart;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCartItem is a Querydsl query type for CartItem
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCartItem extends EntityPathBase<CartItem> {

    private static final long serialVersionUID = -428405964L;

    public static final QCartItem cartItem = new QCartItem("cartItem");

    public final NumberPath<Long> cartId = createNumber("cartId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> orderQuantity = createNumber("orderQuantity", Integer.class);

    public final NumberPath<Long> productId = createNumber("productId", Long.class);

    public QCartItem(String variable) {
        super(CartItem.class, forVariable(variable));
    }

    public QCartItem(Path<? extends CartItem> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCartItem(PathMetadata metadata) {
        super(CartItem.class, metadata);
    }

}

