package com.alexshabanov.gjmdemo.logic.service;

import com.alexshabanov.gjmdemo.logic.model.User;

public interface UserService {

  long register(User user);

  void deleteById(long id);

  User getById(long id);
}
