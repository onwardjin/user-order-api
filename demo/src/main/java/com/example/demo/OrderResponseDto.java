package com.example.demo;

public class OrderResponseDto {
    private Long id;
    private String item;
    private Long userId;
    private String userName;

    public OrderResponseDto(Order order){
        this.id = order.getId();
        this.item = order.getItem();
        this.userId = order.getUser().getId();
        this.userName = order.getUser().getName();
    }

    public Long getId(){
        return id;
    }
    public String getItem(){
        return item;
    }
    public String getUserName(){
        return userName;
    }
    public Long getUserId(){
        return userId;
    }
}