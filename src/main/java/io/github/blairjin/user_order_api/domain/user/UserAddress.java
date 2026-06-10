package io.github.blairjin.user_order_api.domain.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "user_addresses")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserAddress {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private Long userId;

    private String zipCode;
    private String address1;
    private String address2;
    private boolean isDefault;
}