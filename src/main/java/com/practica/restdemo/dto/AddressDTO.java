package com.practica.restdemo.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class AddressDTO implements Serializable {

  private static final long serialVersionUID = 1L;
  private long id;
  private String addressId;
  private String city;
  private String country;
  private String streetName;
  private String postalCode;
  private String type;
  private UserDto userDetails;
}
