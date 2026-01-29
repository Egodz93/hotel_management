package org.example.hotel_managegmet.dto.response;

import lombok.Data;

@Data
public class GuestResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String idNumber;
    private Integer loyaltyPoints;
}
