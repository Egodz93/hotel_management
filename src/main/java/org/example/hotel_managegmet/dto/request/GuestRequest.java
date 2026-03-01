package org.example.hotel_managegmet.dto.request;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class GuestRequest {
    @NotBlank(message = "Tên không được để trống")
    @Size(max = 100)
    private String firstName;

    @NotBlank(message = "Họ không được để trống")
    @Size(max = 100)
    private String lastName;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    private String email;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^[0-9]{9,15}$", message = "Số điện thoại không hợp lệ")
    private String phone;

    private String address;

    @NotBlank(message = "Số CMND/CCCD không được để trống")
    private String idNumber;

    @NotNull(message = "Ngày sinh không được để trống")
    @Past(message = "Ngày sinh phải là ngày trong quá khứ")
    private LocalDate dateOfBirth;
}
