package com.alexshabanov.guicejerseydemo.logic.service;

import com.alexshabanov.guicejerseydemo.logic.model.User;

public interface UserService {

  long register(User user);

  void deleteById(long id);

  User getById(long id);
}
