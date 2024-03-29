package com.carportal.service.impl;

import com.carportal.enums.Roles;
import com.carportal.exception.ExistingUserNameAndEmailException;
import com.carportal.exception.PasswordException;
import com.carportal.exception.UserNotFoundException;
import com.carportal.model.Address;
import com.carportal.model.User;
import com.carportal.payload.AdminDto;
import com.carportal.payload.LoginDto;
import com.carportal.payload.UserDto;
import com.carportal.repository.UserRepository;
import com.carportal.service.UserService;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.util.Strings;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    @Autowired
//    private AddressRepository addressRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {

        if(this.userRepository.findByEmail(userDto.getEmail()) != null){

            throw new ExistingUserNameAndEmailException("User exist with this email/username");
        }
        User user = this.modelMapper.map(userDto,User.class);
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        user.setRole(Roles.USER);
        user.setActive(true);
//        List<Address> addresses = new ArrayList<>();
//        for (Address address : user.getAddresses()) {
//            address.setUser(user);
//            addresses.add(address);
//        }
//        user.setAddresses(addresses);
        User saveUser =this.userRepository.save(user);
        return this.modelMapper.map(saveUser,UserDto.class);
    }

    @Override
    public List<UserDto> getAllUser() {

        List<User> users = this.userRepository.findAll();
        List<UserDto> getAllData = users.stream().map(user -> this.modelMapper.map(user,UserDto.class))
                .collect(Collectors.toList());

        return getAllData;
    }

    @Override
    public UserDto getUser(Integer uId) {

        User user = this.userRepository.findById(uId)
                .orElseThrow(() -> new UserNotFoundException("Failed to update user with ID: " + uId));

        return this.modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto updateUser(int uId, UserDto userDto) {

        User user = this.userRepository.findById(uId)
                    .orElseThrow(() -> new UserNotFoundException("Failed to update user with ID: " + uId));

        if(userDto.getName() != null && !userDto.getName().isEmpty()){
            user.setName(userDto.getName());
        }
        if(userDto.getMobileNo() != null && !userDto.getMobileNo().isEmpty()){
            user.setMobileNo(userDto.getMobileNo());
        }
        if(userDto.getBirthDate()  != null){
            user.setBirthDate(userDto.getBirthDate());
        }

            User updateUser = this.userRepository.save(user);

            return this.modelMapper.map(updateUser,UserDto.class);
    }

    @Transactional
    public void deleteUser(int uId){

        User user = this.userRepository.findById(uId)
                    .orElseThrow(() -> new UserNotFoundException("User not found : " + uId));

        user.setActive(false);
        this.userRepository.save(user);

//        if(user != null){
//
//            List<Address> addressList = addressRepository.findByUser(user);
//            addressRepository.deleteAll(addressList);
//            this.userRepository.delete(user);
//        }
    }

    @Override
    public AdminDto registerAdmin(AdminDto adminDto) {
        User user = userRepository.findByEmail(adminDto.getEmail());

        if(user != null){
            throw new ExistingUserNameAndEmailException("User exist with this email/username");
        }

        User newUser = User.builder().name(adminDto.getName()).username(adminDto.getUsername())
                .email(adminDto.getEmail()).password(this.passwordEncoder.encode(adminDto.getPassword())).mobileNo(adminDto.getMobileNo())
                .role(Roles.ADMIN).active(true).build();

         User adminUser = this.userRepository.save(newUser);

        return this.modelMapper.map(adminUser, AdminDto.class);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword, String cPassword) {

        User user = this.userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        if (!newPassword.equals(cPassword)) {
            throw new PasswordException("New password and confirm password not match");
        }

        if(!passwordEncoder.matches(oldPassword, user.getPassword())){
            throw new PasswordException("Old password does not match");
        }else {
            user.setPassword(this.passwordEncoder.encode(newPassword));
            this.userRepository.save(user);
        }
    }

    @Override
    public void forgotPassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        this.userRepository.save(user);
    }
}


