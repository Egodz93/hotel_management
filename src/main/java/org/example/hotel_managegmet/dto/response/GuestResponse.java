package org.example.hotel_managegmet.dto.response;
import lombok.Data;
import java.time.LocalDate;
@Data
public class GuestResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String idNumber;
    private LocalDate dateOfBirth;
    private Integer loyaltyPoints;
    private Double loyaltyDiscount;
}
