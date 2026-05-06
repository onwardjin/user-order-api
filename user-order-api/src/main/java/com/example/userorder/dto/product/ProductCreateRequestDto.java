package com.example.userorder.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ProductCreateRequestDto(
        @NotBlank(message = "제품 이름은 필수입니다.")
        String name,

        @NotNull(message = "금액은 필수입니다.")
        @PositiveOrZero(message = "금액은 0 이상이여야 합니다.")
        Integer price,

        @NotNull(message = "재고는 필수입니다.")
        @PositiveOrZero(message = "재고는 0 이상이여야 합니다.")
        Integer stockQuantity
) {
}