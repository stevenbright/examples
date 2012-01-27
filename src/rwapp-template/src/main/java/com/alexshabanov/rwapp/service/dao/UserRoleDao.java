package com.alexshabanov.rwapp.service.dao;

import com.alexshabanov.rwapp.model.user.UserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.MANDATORY)
public interface UserRoleDao extends CrudRepository<UserRole, Long> {
    UserRole findByCode(String code);
}
