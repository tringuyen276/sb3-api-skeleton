//package com.digiex.utility.config;
//
//import com.digiex.utility.web.model.User;
//import com.digiex.utility.web.service.UserService;
//import jakarta.annotation.PostConstruct;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class DataInitializer {
//
//  @Autowired private UserService userService;
//
//  @PostConstruct
//  public void init() {
//    String username = "admin";
//    String password = "123456789";
//    String firstName = "admin";
//    String lastName = "general";
//
//    if (userService.findUserByUsername(username) == null) {
//      User adminUser =
//          User.builder()
//              .username(username)
//              .password(password)
//              .firstName(firstName)
//              .lastName(lastName)
//              .email("admin@example.com") // Add a default email
//              .build();
//      userService.saveUser(adminUser);
//    }
//  }
//}
