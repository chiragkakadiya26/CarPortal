package com.carportal.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class RegisterDto {

    private String name;
    private String username;
    private String email;
    private String password;
    private String mobileNo;
    private Boolean active;
    private LocalDate birthDate;
}
