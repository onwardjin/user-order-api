//package com.example.userorder.value;
//
//public class Money {
//    private Integer value;
//
//    private Money(Integer value) {
//        if (value == null || value < 0) {
//            throw new RuntimeException();
//        }
//        this.value = value;
//    }
//
//    public static Money of(Integer value) {
//        return new Money(value);
//    }
//
//    public Money multiply(Integer quantity) {
//        if (quantity == null || quantity <= 0) {
//            throw new RuntimeException();
//        }
//
//        return new Money(this.value * quantity);
//    }
//}
