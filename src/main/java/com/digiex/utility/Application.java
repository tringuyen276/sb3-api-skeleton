package com.digiex.utility;

import com.digiex.utility.web.model.Permission;
import com.digiex.utility.web.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Set;
import java.util.UUID;

@SpringBootApplication
public class Application {

  @Autowired
  private RoleService roleService;


  public static void main(String[] args) {
    SpringApplication.run(Application.class, args).start();
  }

}
