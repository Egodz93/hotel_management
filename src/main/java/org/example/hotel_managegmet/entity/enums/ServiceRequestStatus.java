package org.example.hotel_managegmet.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum ServiceRequestStatus {
    PENDING(1, "Pending"),
    IN_PROGRESS(2, "In Progress"),
    COMPLETED(3, "Completed"),
    CANCELLED(4, "Cancelled");

    private final int code;
    private final String displayName;

    public static ServiceRequestStatus fromCode(int code) {
        return Arrays.stream(values())
                .filter(s -> s.code == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid service request status code: " + code));
    }
}
