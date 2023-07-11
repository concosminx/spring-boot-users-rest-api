package com.practica.restdemo.ui.model.request;

import lombok.Data;

import java.util.List;

@Data
public class UserRequestBodyModel {
  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private List<AddressRequestModel> addresses;
}
