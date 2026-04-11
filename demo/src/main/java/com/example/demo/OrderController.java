package com.example.demo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @PostMapping
    public OrderResponseDto createOrder(@RequestBody OrderRequestDto request){
        return orderService.create(request);
    }

    @GetMapping
    public List<OrderResponseDto> getOrders(){
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    public OrderResponseDto getOrder(@PathVariable Long id){
        return orderService.findById(id);
    }

    @PutMapping("/{id}")
    public OrderResponseDto update(@PathVariable Long id, @RequestBody OrderRequestDto request){
        return orderService.update(id, request);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        orderService.delete(id);
    }
}