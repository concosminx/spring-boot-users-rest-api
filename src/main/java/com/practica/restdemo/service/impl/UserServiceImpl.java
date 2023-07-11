package com.practica.restdemo.service.impl;


import com.practica.restdemo.dto.AddressDTO;
import com.practica.restdemo.dto.UserDto;
import com.practica.restdemo.entity.UserEntity;
import com.practica.restdemo.exceptions.UserServiceException;
import com.practica.restdemo.repository.UserRepository;
import com.practica.restdemo.service.UserService;
import com.practica.restdemo.ui.model.ErrorMessages;
import com.practica.restdemo.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private Utils utils;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public UserDto createUser(UserDto user) {
    if (userRepository.findByEmail(user.getEmail()) != null) {
      throw new UserServiceException(ErrorMessages.RECORD_ALREADY_EXISTS.getErrorMessage());
    }

    for(int i=0;i<user.getAddresses().size();i++) {
      AddressDTO address = user.getAddresses().get(i);
      address.setUserDetails(user);
      address.setAddressId(utils.generateAddressId(30));
      user.getAddresses().set(i, address);
    }

    ModelMapper modelMapper = new ModelMapper();

    UserEntity userEntity = modelMapper.map(user, UserEntity.class);
    userEntity.setEncryptedPassword(passwordEncoder.encode(user.getPassword()));
    String publicUserId = utils.generateUserId(30);
    userEntity.setUserId(publicUserId);

    UserEntity storedUserDetails = userRepository.save(userEntity);

    UserDto returnValue  = modelMapper.map(storedUserDetails, UserDto.class);

    return returnValue;
  }

  @Override
  public UserDto getUser(String email) {
    UserEntity userEntity = userRepository.findByEmail(email);
    if (userEntity == null) throw new UsernameNotFoundException(email);

    ModelMapper mm = new ModelMapper();
    UserDto returnValue = mm.map(userEntity, UserDto.class);

    return returnValue;
  }

  @Override
  public UserDto getUserByUserId(String userId) {
    UserEntity userEntity = userRepository.findByUserId(userId);
    if (userEntity == null) throw new UsernameNotFoundException(userId);

    ModelMapper mm = new ModelMapper();
    UserDto userDto = mm.map(userEntity, UserDto.class);

    return userDto;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    UserEntity userEntity = userRepository.findByEmail(email);
    if (userEntity == null) throw new UsernameNotFoundException(email);

    return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(),
        true,
        true, true,
        true, Collections.emptyList());

  }

  @Override
  public UserDto updateUser(String userId, UserDto user) {
    UserEntity userEntity = userRepository.findByUserId(userId);

    if (userEntity == null)
      throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

    userEntity.setFirstName(user.getFirstName());
    userEntity.setLastName(user.getLastName());

    UserEntity updatedUserDetails = userRepository.save(userEntity);
    ModelMapper mm = new ModelMapper();
    UserDto returnValue = mm.map(updatedUserDetails, UserDto.class);

    return returnValue;
  }

  @Override
  public void deleteUser(String userId) {
    UserEntity userEntity = userRepository.findByUserId(userId);

    if (userEntity == null)
      throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

    userRepository.delete(userEntity);
  }

  @Override
  public List<UserDto> getUsers(int page, int limit) {
    List<UserDto> returnValue = new ArrayList<>();

    if(page>0) page = page-1;

    Pageable pageableRequest = PageRequest.of(page, limit);

    Page<UserEntity> usersPage = userRepository.findAll(pageableRequest);
    List<UserEntity> users = usersPage.getContent();

    for (UserEntity userEntity : users) {
      new UserDto();
      ModelMapper mm = new ModelMapper();
      UserDto userDto = mm.map(userEntity, UserDto.class);
      returnValue.add(userDto);
    }

    return returnValue;
  }


}
