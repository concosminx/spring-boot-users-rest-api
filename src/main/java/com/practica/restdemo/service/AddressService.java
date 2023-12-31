package com.practica.restdemo.service;

import com.practica.restdemo.dto.AddressDTO;

import java.util.List;

public interface AddressService {

  List<AddressDTO> getAddresses(String userId);
  AddressDTO getAddress(String addressId);
}
