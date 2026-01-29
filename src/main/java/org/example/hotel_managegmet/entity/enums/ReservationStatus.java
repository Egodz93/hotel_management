package org.example.hotel_managegmet.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum ReservationStatus {
    PENDING(1, "Pending Confirmation"),
    CONFIRMED(2, "Confirmed"),
    CHECKED_IN(3, "Checked In"),
    CHECKED_OUT(4, "Checked Out"),
    CANCELLED(5, "Cancelled"),
    NO_SHOW(6, "No Show");

    private final int code;
    private final String displayName;

    public static ReservationStatus fromCode(int code) {
        return Arrays.stream(values())
                .filter(s -> s.code == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid reservation status code: " + code));
    }
}
