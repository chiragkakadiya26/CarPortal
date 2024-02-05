package com.carportal.model;

import com.carportal.enums.Roles;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uId;
    @Column(columnDefinition = "varchar default name '' ")
    private String name;
    @Column(columnDefinition = "varchar default '' ")
    private String username;
    @Column(columnDefinition = "varchar default '' ")
    private String email;
    @Column(columnDefinition = "varchar default '' ")
    @JsonIgnore
    private String password;
    @Column(columnDefinition = "varchar default '' ")
    private String mobileNo;
    @Enumerated(EnumType.STRING)
    private Roles role;
    private Boolean active;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JsonManagedReference // while fetching error then using that on relationship mapping then resolve
//    private List<Address> addresses = new ArrayList<>();
}
