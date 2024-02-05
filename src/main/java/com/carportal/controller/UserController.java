package com.carportal.controller;

import com.carportal.model.Address;
import com.carportal.payload.AdminDto;
import com.carportal.payload.LoginDto;
import com.carportal.payload.ResponseApi;
import com.carportal.payload.UserDto;
import com.carportal.service.AddressService;
import com.carportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

//    @PostMapping("/createuser")
//    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
//
//        UserDto createUser =  this.userService.createUser(userDto);
//        return new ResponseEntity<>(createUser, HttpStatus.CREATED);
//    }

   @GetMapping
    public ResponseEntity<List<UserDto>> getAllData(){

        return ResponseEntity.ok(this.userService.getAllUser());
    }

    @GetMapping("/{uId}")
    public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer uId){

        return ResponseEntity.ok(this.userService.getUser(uId));
    }

    @PutMapping("/{uId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable int uId, @RequestBody UserDto userDto){

        UserDto updateUser = this.userService.updateUser(uId,userDto);
        return ResponseEntity.ok(updateUser);
    }

    @DeleteMapping("/{uId}")
    public ResponseEntity<ResponseApi> deleteUser(@PathVariable int uId){
        this.userService.deleteUser(uId);
        ResponseApi responseApi = new ResponseApi("User Deleted Successfully..", true , null,"Success" );
        responseApi.dateTimeFormatter();
        return new ResponseEntity<>(responseApi,HttpStatus.OK);
    }
}
