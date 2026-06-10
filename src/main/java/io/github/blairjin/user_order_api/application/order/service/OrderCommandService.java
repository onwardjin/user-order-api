package io.github.blairjin.user_order_api.application.order.service;

import io.github.blairjin.user_order_api.application.cart.reader.CartReader;
import io.github.blairjin.user_order_api.application.order.command.OrderCommandMapper;
import io.github.blairjin.user_order_api.application.order.command.OrderItemCommand;
import io.github.blairjin.user_order_api.application.order.reader.OrderReader;
import io.github.blairjin.user_order_api.application.product.reader.ProductReader;
import io.github.blairjin.user_order_api.domain.cart.CartItem;
import io.github.blairjin.user_order_api.domain.order.Order;
import io.github.blairjin.user_order_api.domain.order.OrderItem;
import io.github.blairjin.user_order_api.domain.order.OrderStatus;
import io.github.blairjin.user_order_api.domain.order.vo.OrderQuantity;
import io.github.blairjin.user_order_api.domain.product.Product;
import io.github.blairjin.user_order_api.exception.BAD_REQUEST.EmptyCartException;
import io.github.blairjin.user_order_api.exception.NOT_FOUND.ProductNotFoundException;
import io.github.blairjin.user_order_api.repository.cart.CartItemRepository;
import io.github.blairjin.user_order_api.repository.cart.CartRepository;
import io.github.blairjin.user_order_api.repository.order.OrderItemRepository;
import io.github.blairjin.user_order_api.repository.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderCommandService {
    private final CartReader cartReader;
    private final ProductReader productReader;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderReader orderReader;

    @Transactional
    public void createFromCart(Long userId){
        Long cartId = cartReader.getCartIdByUserId(userId);
        List<CartItem> items = getCartItems(cartId);

        Map<Long, Product> productMap = getProductMap(items);

        Order order = orderRepository.save(Order.create(userId));

        List<OrderItem> orderItems =
                items.stream().map(cartItem -> {
                    Product product = productMap.get(cartItem.getProductId());
                    product.decrease(cartItem.getOrderQuantity());

                    OrderItemCommand command = OrderCommandMapper.toCommand(product, cartItem);
                    return OrderItem.create(order.getId(), command);
                }).toList();

        updateTotalPrice(order, orderItems);
        orderItemRepository.saveAll(orderItems);

        cartItemRepository.deleteAllByCartId(cartId);
    }

    @Transactional
    public void createDirect(Long userId, Long productId, OrderQuantity orderQuantity){
        Product product = productReader.getProductById(productId);
        product.decrease(orderQuantity);

        Order order = orderRepository.save(Order.create(userId));

        OrderItemCommand command = OrderCommandMapper.toCommand(product, orderQuantity);
        OrderItem item = OrderItem.create(order.getId(), command);
        orderItemRepository.save(item);

        order.updateTotalPrice(item.getTotalPrice());
    }

    @Transactional
    public void cancel(Long userId, Long orderId){
        Order order = orderReader.getOrderByUserIdAndId(userId, orderId);
        order.changeStatus(OrderStatus.CANCELED);
    }

    private List<CartItem> getCartItems(Long cartId){
        List<CartItem> items = cartReader.getItemsByCartId(cartId);

        if(items.isEmpty()){
            throw new EmptyCartException();
        }

        return items;
    }

    private Map<Long, Product> getProductMap(List<CartItem> items){
        List<Long> productIds = items.stream()
                .map(CartItem::getProductId)
                .distinct()
                .sorted()
                .toList();

        List<Product> products = productReader.getAllByIds(productIds);

        if (products.size() != productIds.size()) {
            throw new ProductNotFoundException();
        }

        return products.stream()
                .collect(Collectors.toMap(Product::getId, product->product));
    }

    private void updateTotalPrice(Order order, List<OrderItem> items){
        long totalPrice = items.stream()
                .mapToLong(OrderItem::getTotalPrice)
                .sum();

        order.updateTotalPrice(totalPrice);
    }
}
