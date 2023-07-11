package com.practica.restdemo.ui.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class AddressesRest {
  private String addressId;
  private String city;
  private String country;
  private String streetName;
  private String postalCode;
  private String type;
}
