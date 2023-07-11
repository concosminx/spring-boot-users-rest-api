package com.practica.restdemo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="users")
@Getter
@Setter
@NoArgsConstructor
public class UserEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue
  private long id;

  @Column(nullable=false)
  private String userId;

  @Column(nullable=false, length=50)
  private String firstName;

  @Column(nullable=false, length=50)
  private String lastName;

  @Column(nullable=false, length=120/*, unique = true*/)
  private String email;

  @Column(nullable=false)
  private String encryptedPassword;

  @OneToMany(mappedBy="userDetails", cascade=CascadeType.ALL, fetch = FetchType.EAGER)
  private List<AddressEntity> addresses;

}
