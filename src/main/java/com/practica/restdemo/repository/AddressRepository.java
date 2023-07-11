package com.practica.restdemo.repository;

import com.practica.restdemo.entity.AddressEntity;
import com.practica.restdemo.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends CrudRepository<AddressEntity, Long> {
  List<AddressEntity> findAllByUserDetails(UserEntity userEntity);
  AddressEntity findByAddressId(String addressId);
}

