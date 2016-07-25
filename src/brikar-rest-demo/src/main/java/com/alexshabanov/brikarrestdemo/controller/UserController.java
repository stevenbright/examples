package com.alexshabanov.brikarrestdemo.controller;

import com.alexshabanov.demo.useraccount.UserModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Alexander Shabanov
 */
@Controller
@RequestMapping("rest/user")
public final class UserController {

  @RequestMapping("{id}")
  @ResponseBody
  public UserModel.UserAccount hello(@PathVariable String id) {
    if ("0".equals(id)) {
      return UserModel.UserAccount.getDefaultInstance();
    }

    return UserModel.UserAccount.newBuilder()
        .setId(id)
        .setNickname("Echo1")
        .setPasswordHash("pC3vOskl0HuaYPnVX4UVHJmFOrILgk2DPn7fN84iii2rg1k325zqeYAlSGuZU1bA")
        .addContacts(UserModel.Contact.newBuilder().setNumber("+1 123 456 78 90").setType(UserModel.ContactType.PHONE).build())
        .addContacts(UserModel.Contact.newBuilder().setNumber("test@localhost").setType(UserModel.ContactType.EMAIL).build())
        .addContacts(UserModel.Contact.newBuilder().setNumber("sample@demo").setType(UserModel.ContactType.EMAIL).build())
        .addContacts(UserModel.Contact.newBuilder().setNumber("urn:twitter:12345678").setType(UserModel.ContactType.UNKNOWN).build())
        .addAuthorities("ROLE_USER")
        .addAuthorities("ROLE_READER")
        .addAuthorities("ROLE_UPLOADER")
        .setCreated(System.currentTimeMillis())
        .setActive(true)
        .build();
  }
}
