package io.github.blairjin.user_order_api.domain.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserAddress is a Querydsl query type for UserAddress
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserAddress extends EntityPathBase<UserAddress> {

    private static final long serialVersionUID = -618701485L;

    public static final QUserAddress userAddress = new QUserAddress("userAddress");

    public final StringPath address1 = createString("address1");

    public final StringPath address2 = createString("address2");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isDefault = createBoolean("isDefault");

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public final StringPath zipCode = createString("zipCode");

    public QUserAddress(String variable) {
        super(UserAddress.class, forVariable(variable));
    }

    public QUserAddress(Path<? extends UserAddress> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserAddress(PathMetadata metadata) {
        super(UserAddress.class, metadata);
    }

}

