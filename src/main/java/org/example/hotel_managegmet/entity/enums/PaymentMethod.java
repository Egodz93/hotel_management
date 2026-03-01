package org.example.hotel_managegmet.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum PaymentMethod {
    CASH(1, "Cash"),
    CREDIT_CARD(2, "Credit Card"),
    BANK_TRANSFER(3, "Bank Transfer"),
    E_WALLET(4, "E-Wallet");

    private final int code;
    private final String displayName;

    public static PaymentMethod fromCode(int code) {
        return Arrays.stream(values())
                .filter(s -> s.code == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid payment method code: " + code));
    }
}
