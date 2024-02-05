package com.carportal.service.impl;

import com.carportal.exception.UserNotFoundException;
import com.carportal.model.Address;
import com.carportal.model.User;
import com.carportal.repository.UserRepository;
import com.carportal.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public List<Address> getAllAddress() {

        List<Address> allAddresses = new ArrayList<>();
        for (User user : userRepository.findAll()) {
//            allAddresses.addAll(user.getAddress());
        }
        System.out.println(allAddresses);
        return allAddresses;
    }

//    @Override
//    public List<Address> getAddress(Integer uId) {
//        User user = this.userRepository.findById(uId)
//                .orElseThrow(() -> new UserNotFoundException("Not found user with ID To address: " + uId));
//
//        if(user == null) {
//            return new ArrayList<>();
//        }
//
//        return user.getAddress();
//    }

    @Override
    public void addAddress(Integer uId, Address address) {

        User user = this.userRepository.findById(uId)
                .orElseThrow(() -> new UserNotFoundException("Not found user with Id To address: " + uId));

        if(user != null){
//            user.getAddress().add(address);
            userRepository.save(user);
        }
    }

    @Override
    public Address updateAddress(Integer uId, Address address) {

        User user = this.userRepository.findById(uId)
                .orElseThrow(() -> new UserNotFoundException("NOt found user with Id to Address : " + uId));
        if(user != null){

            address.setCity(address.getCity());
            address.setState(address.getState());
            address.setStreet(address.getStreet());
            address.setPostalCode(address.getPostalCode());
//            user.getAddress().removeIf(oldaddress -> oldaddress.equals(address));
//            user.getAddress().add(address);
            userRepository.save(user);
        }
        return new Address();
    }

    @Override
    public void deleteAddress(Integer uId, Address address) {

        User user = this.userRepository.findById(uId)
                .orElseThrow(()->  new UserNotFoundException("NOt found user with Id to Address : " + uId));

        if(user != null){

//            user.getAddress().remove(address);
            userRepository.save(user);
        }
    }


}
