package com.carportal.payload;

import com.carportal.enums.Roles;
import com.carportal.model.Address;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private int uId;

    @NotBlank(message = "Name cannot be blank")
    private String name;

//    @NotBlank(message = "User cannot be blank")
    private String username;

//    @NotBlank(message = "Email cannot be blank")
    private String email;

    private String password;

    @NotBlank(message = "Mobile number cannot be blank")
    @Pattern(regexp = "\\d{10}", message = "Mobile number must be 10 digits")
    private String mobileNo;

    private Date createDate;
    private Date updateDate;
    private Roles roles;
    private Boolean active;

    @NotNull(message = "Birth date cannot be null")
    private LocalDate birthDate;
//    private List<Address> addresses = new ArrayList<>();
}