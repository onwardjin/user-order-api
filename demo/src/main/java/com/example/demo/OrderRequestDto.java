package com.example.demo;

public class OrderRequestDto {
    private String item;
    private Long userId;

    public String getItem(){
        return item;
    }

    public Long getUserId(){
        return userId;
    }
}
