package com.carportal.payload;

import com.carportal.enums.Roles;
import com.carportal.model.Auditable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDto extends Auditable {

    private int uId;
    private String name;
    private String username;
    private String email;
    private String password;
    private String mobileNo;
    private Roles role;
}
