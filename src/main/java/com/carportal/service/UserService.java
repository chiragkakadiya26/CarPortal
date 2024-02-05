package com.carportal.service;

import com.carportal.payload.AdminDto;
import com.carportal.payload.LoginDto;
import com.carportal.payload.UserDto;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto userDto);

    List<UserDto> getAllUser();

    UserDto getUser(Integer uId);

    UserDto updateUser(int uId, UserDto userDto);

    void deleteUser(int uId);

    AdminDto registerAdmin(AdminDto adminDto);



//    LoginDto login(String email, String password);
}
