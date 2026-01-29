package org.example.hotel_managegmet.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum RoomStatus {
    AVAILABLE(1, "Available"),
    OCCUPIED(2, "Occupied"),
    RESERVED(3, "Reserved"),
    UNDER_MAINTENANCE(4, "Under Maintenance"),
    CLEANING(5, "Cleaning");

    private final int code;
    private final String displayName;

    public static RoomStatus fromCode(int code) {
        return Arrays.stream(values())
                .filter(s -> s.code == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid room status code: " + code));
    }
}
