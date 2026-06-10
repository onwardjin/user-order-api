package io.github.blairjin.user_order_api.application.product.command;

import io.github.blairjin.user_order_api.domain.common.vo.Money;
import io.github.blairjin.user_order_api.domain.product.vo.ProductName;
import io.github.blairjin.user_order_api.domain.product.vo.StockQuantity;
import io.github.blairjin.user_order_api.dto.product.ProductCreateRequest;
import io.github.blairjin.user_order_api.dto.product.ProductUpdateRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ProductCommandMapper {
    public static ProductCreateCommand toCommand(ProductCreateRequest request){
        return new ProductCreateCommand(
                ProductName.of(request.productName()),
                StockQuantity.of(request.stockQuantity()),
                Money.of(request.unitPrice())
        );
    }

    public static ProductUpdateCommand toCommand(ProductUpdateRequest request){
        return new ProductUpdateCommand(
                request.productName() != null
                        ? ProductName.of(request.productName())
                        : null,
                request.stockQuantity() != null
                        ? StockQuantity.of(request.stockQuantity())
                        : null,
                request.unitPrice() != null
                        ? Money.of(request.unitPrice())
                        : null,
                request.productStatus() != null
                        ? request.productStatus()
                        :null
        );
    }
}