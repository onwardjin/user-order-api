package com.example.userorder.domain.order;

import java.util.ArrayList;
import java.util.List;

public enum OrderStatus {
    PAYMENT_PENDING,
    ORDERED,
    DELIVERING,
    DELIVERED,
    CANCELED;
}