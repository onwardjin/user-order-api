package io.github.blairjin.user_order_api.application.product.service;

import io.github.blairjin.user_order_api.application.product.command.ProductCreateCommand;
import io.github.blairjin.user_order_api.application.product.command.ProductUpdateCommand;
import io.github.blairjin.user_order_api.application.product.reader.ProductReader;
import io.github.blairjin.user_order_api.domain.product.Product;
import io.github.blairjin.user_order_api.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductCommandService {
    private final ProductReader productReader;
    private final ProductRepository productRepository;

    @Transactional
    public void create(Long userId, ProductCreateCommand command){
        Product product = Product.create(userId, command);
        productRepository.save(product);
    }

    @Transactional
    public void update(Long userId, Long productId, ProductUpdateCommand command){
        Product product = productReader.getProductByUserIdAndId(userId, productId);
        product.update(command);
    }

    @Transactional
    public void delete(Long userId, Long productId){
        Product product = productReader.getProductByUserIdAndId(userId, productId);
        productRepository.delete(product);
    }
}