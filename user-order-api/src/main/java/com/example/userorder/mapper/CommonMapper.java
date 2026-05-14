package com.example.userorder.mapper;

import com.example.userorder.domain.common.vo.Money;

public class CommonMapper {
    public static Money toMoney(long money) {
        return Money.of(money);
    }
}