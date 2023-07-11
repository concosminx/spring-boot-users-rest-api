package com.practica.restdemo.service.impl;


import com.practica.restdemo.dto.AddressDTO;
import com.practica.restdemo.entity.AddressEntity;
import com.practica.restdemo.entity.UserEntity;
import com.practica.restdemo.repository.AddressRepository;
import com.practica.restdemo.repository.UserRepository;
import com.practica.restdemo.service.AddressService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
  @Autowired
  UserRepository userRepository;

  @Autowired
  AddressRepository addressRepository;

  @Override
  public List<AddressDTO> getAddresses(String userId) {
    List<AddressDTO> returnValue = new ArrayList<>();
    ModelMapper modelMapper = new ModelMapper();

    UserEntity userEntity = userRepository.findByUserId(userId);
    if(userEntity==null) return returnValue;

    Iterable<AddressEntity> addresses = addressRepository.findAllByUserDetails(userEntity);
    for(AddressEntity addressEntity:addresses)
    {
      returnValue.add( modelMapper.map(addressEntity, AddressDTO.class) );
    }

    return returnValue;
  }

  @Override
  public AddressDTO getAddress(String addressId) {
    AddressDTO returnValue = null;

    AddressEntity addressEntity = addressRepository.findByAddressId(addressId);

    if(addressEntity!=null)
    {
      returnValue = new ModelMapper().map(addressEntity, AddressDTO.class);
    }

    return returnValue;
  }
}
