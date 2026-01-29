package org.example.hotel_managegmet.dto.request;

import lombok.Data;

@Data
public class GuestRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String idNumber;
    private Integer loyaltyPoints;
}
