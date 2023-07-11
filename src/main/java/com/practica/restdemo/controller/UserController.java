package com.practica.restdemo.controller;


import com.practica.restdemo.dto.UserDto;
import com.practica.restdemo.service.AddressService;
import com.practica.restdemo.service.UserService;
import com.practica.restdemo.ui.model.request.UserRequestBodyModel;
import com.practica.restdemo.ui.model.response.UserRest;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private AddressService addressService;


  @ApiOperation(value="The Get User Details Web Service Endpoint")
  @GetMapping(path = "/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
  public UserRest getUser(@PathVariable("userId") String userId) {
    UserDto userDto = userService.getUserByUserId(userId);
    ModelMapper modelMapper = new ModelMapper();
    UserRest returnObject = modelMapper.map(userDto, UserRest.class);
    return returnObject;
  }


  @PostMapping(
      consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE}
  )
  public UserRest createUser(@RequestBody UserRequestBodyModel userDetails) {
    ModelMapper modelMapper = new ModelMapper();

    UserDto userDto = modelMapper.map(userDetails, UserDto.class);

    UserDto createdUser = userService.createUser(userDto);
    UserRest userRest = modelMapper.map(createdUser, UserRest.class);

    return userRest;
  }




}
