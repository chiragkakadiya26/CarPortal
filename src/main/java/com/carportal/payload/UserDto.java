package com.carportal.payload;

import com.carportal.enums.Roles;
import com.carportal.model.Address;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
    private String name;
    private String username;
    private String email;
    private String password;
    private String mobileNo;
    private Date createDate;
    private Date updateDate;
    private Roles roles;
    private Boolean active;
    private LocalDate birthDate;
//    private List<Address> addresses = new ArrayList<>();
}