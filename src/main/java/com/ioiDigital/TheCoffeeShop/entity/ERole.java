package com.ioiDigital.TheCoffeeShop.entity;

public enum ERole {
  GLOBAL_ADMIN("Global_Admin"),
  ADMIN("Admin"),
  EMPLOYEE("Employee"),
  CUSTOMER("Customer");

  private final String roleName;

  ERole(String roleName) {
    this.roleName = roleName;
  }

  public String getRoleName() {
    return roleName;
  }
}
