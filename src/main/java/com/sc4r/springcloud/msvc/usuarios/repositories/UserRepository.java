package com.sc4r.springcloud.msvc.usuarios.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.sc4r.springcloud.msvc.usuarios.models.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {

  Optional<User> findByEmail(String email);

  @Query("select u from User u where u.email= ?1")
  Optional<User> porEmail(String email);

  boolean existsByEmail(String email);

}
