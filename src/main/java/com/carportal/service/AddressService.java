package com.carportal.service;

import com.carportal.model.Address;

import java.util.List;

public interface AddressService {

    List<Address> getAllAddress();

//    List<Address> getAddress(Integer uId);

    void addAddress(Integer uId, Address address);

    Address updateAddress(Integer uId, Address address);

    void deleteAddress(Integer uId, Address address);
}
