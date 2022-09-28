package com.sc4r.springcloud.msvc.usuarios.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sc4r.springcloud.msvc.usuarios.clients.CursoClienteRest;
import com.sc4r.springcloud.msvc.usuarios.models.entity.User;
import com.sc4r.springcloud.msvc.usuarios.repositories.UserRepository;
import com.sc4r.springcloud.msvc.usuarios.services.UserService;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  UserRepository userRepository;

  @Autowired
  CursoClienteRest cursoClienteRest;

  @Override
  @Transactional(readOnly = true)
  public List<User> getUsers() {
    // List<User> userList = new ArrayList<>();
    return (List<User>) userRepository.findAll();
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<User> findUserById(Long id) {
    return userRepository.findById(id);
  }

  @Override
  @Transactional
  public User saveUser(User usuario) {
    return userRepository.save(usuario);
  }

  @Override
  @Transactional
  public void deleteUserById(Long id) {
    userRepository.deleteById(id);
    cursoClienteRest.eliminarCursoUsuarioPorId(id);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<User> findByEmail(String email) {
    return userRepository.findByEmail(email);
    // return userRepository.porEmail(email);
  }

  @Override
  @Transactional(readOnly = true)
  public boolean existePorEmail(String email) {
    return userRepository.existsByEmail(email);
    // return userRepository.porEmail(email);
  }

  @Override
  @Transactional(readOnly = true)
  public List<User> listarPorIds(Iterable<Long> ids) {
    return (List<User>) userRepository.findAllById(ids);
  }

}
