package com.sc4r.springcloud.msvc.usuarios.services;

import java.util.List;
import java.util.Optional;

import com.sc4r.springcloud.msvc.usuarios.models.entity.User;

public interface UserService {
  List<User> getUsers();

  Optional<User> findUserById(Long id);

  User saveUser(User usuario);

  void deleteUserById(Long id);

  List<User> listarPorIds(Iterable<Long> ids);

  Optional<User> findByEmail(String email);

  boolean existePorEmail(String email);
}
