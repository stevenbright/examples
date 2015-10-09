package com.alexshabanov.gjmdemo.logic.service.mapper;

import com.alexshabanov.gjmdemo.logic.model.User;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper {

  @Select("SELECT id FROM user_profile WHERE username=#{value}")
  Long getUserIdByUsername(String username);

  @Select("SELECT id, username, age, note FROM user_profile")
  @Results({
      @Result(property = "id", column = "id", id = true),
      @Result(property = "username", column = "username"),
      @Result(property = "age", column = "age"),
      @Result(property = "note", column = "note")
  })
  List<User> getUsers();

  @Select("SELECT id, username, age, note FROM user_profile WHERE id=#{value}")
  @ResultMap("getUsers-void")
  User getUser(long userId);
}
