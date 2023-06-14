package com.ioiDigital.TheCoffeeShop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Roles")
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String name;

}
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity
//@Table(name = "roles")
//public class Role {
//  @Id
//  @GeneratedValue(strategy = GenerationType.IDENTITY)
//  private long id;
//
//  @Enumerated(EnumType.STRING)
//  @Column(length = 20)
//  private ERole name;
//
////  public Role(ERole name) {
////    this.name = name;
////  }
////
////   public ERole getName() {
////    return name;
////  }
////
////  public void setName(ERole name) {
////    this.name = name;
////  }
