package com.carportal.payload;

import com.carportal.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

    private String email;
    private String password;
    private List<String> role = new ArrayList<>();
}
