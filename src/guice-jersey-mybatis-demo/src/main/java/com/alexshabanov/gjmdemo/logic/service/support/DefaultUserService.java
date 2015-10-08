package com.alexshabanov.gjmdemo.logic.service.support;

import com.alexshabanov.gjmdemo.logic.model.User;
import com.alexshabanov.gjmdemo.logic.service.UserService;

import java.util.HashMap;
import java.util.Map;

public class DefaultUserService implements UserService {

  private final Map<Long, User> users = new HashMap<>();

  public DefaultUserService() {
    final User user = new User();
    user.setId(1L);
    user.setAge(18);
    user.setUsername("alice");
    user.setNote("SysAdmin");
    users.put(user.getId(), user);
  }

  @Override
  public long register(User user) {
    final long nextId = users.keySet().stream().mapToLong(v -> v).max().orElse(0) + 1;
    final User newUser = user.copy();
    newUser.setId(nextId);

    users.put(nextId, newUser);
    return 0;
  }

  @Override
  public void deleteById(long id) {
    users.remove(id);
  }

  @Override
  public User getById(long id) {
    return users.get(id);
  }
}
