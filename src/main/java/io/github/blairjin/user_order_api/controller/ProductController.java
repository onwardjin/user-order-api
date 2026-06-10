package io.github.blairjin.user_order_api.controller;

import io.github.blairjin.user_order_api.application.product.usecase.*;
import io.github.blairjin.user_order_api.dto.product.ProductCreateRequest;
import io.github.blairjin.user_order_api.dto.product.ProductResponse;
import io.github.blairjin.user_order_api.dto.product.ProductUpdateRequest;
import io.github.blairjin.user_order_api.dto.product.SearchProductCondition;
import io.github.blairjin.user_order_api.infrastructure.jwt.CustomUserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final CreateProductUseCase createProductUseCase;
    private final GetProductUseCase getProductUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final DeleteProductUseCase deleteProductUseCase;
    private final SearchProductUseCase searchProductUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @Valid @RequestBody ProductCreateRequest request
    ) {
        createProductUseCase.execute(principal.userId(), request);
    }

    @GetMapping
    public Slice<ProductResponse> searchProducts(SearchProductCondition condition, Pageable pageable){
        return searchProductUseCase.execute(condition, pageable);
    }

    @GetMapping("/{productId}")
    public ProductResponse get(@PathVariable Long productId){
        return getProductUseCase.execute(productId);
    }

    @PatchMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @PathVariable Long productId,
            @Valid @RequestBody ProductUpdateRequest request
    ){
        updateProductUseCase.execute(principal.userId(), productId, request);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @PathVariable Long productId
    ) {
        deleteProductUseCase.execute(principal.userId(), productId);
    }
}