package com.sc4r.springcloud.msvc.usuarios.controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lowagie.text.DocumentException;
import com.sc4r.springcloud.msvc.usuarios.models.entity.User;
import com.sc4r.springcloud.msvc.usuarios.services.UserService;
import com.sc4r.springcloud.msvc.usuarios.services.impl.PdfServiceImpl;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private ApplicationContext context;

  @GetMapping("/crash")
  public void crash() {
    ((ConfigurableApplicationContext) context).close();
  }

  @GetMapping()
  public List<User> getUsers() {
    return userService.getUsers();
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getUserById(@PathVariable Long id) {
    Optional<User> existUser = userService.findUserById(id);
    if (existUser.isPresent()) {
      return ResponseEntity.ok(existUser.get());
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping
  // @ResponseStatus(code = HttpStatus.CREATED)
  public ResponseEntity<?> createUser(@Valid @RequestBody User user, BindingResult result) {

    if (!user.getEmail().isEmpty() && userService.findByEmail(user.getEmail()).isPresent()) {
      return ResponseEntity.badRequest()
          .body(Collections.singletonMap("Mensaje", "Ya existe usuario con ese correo electronico!"));
    }

    if (result.hasErrors()) {
      return validate(result);
    }

    return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(user));
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateUser(@Valid @RequestBody User user, BindingResult result, @PathVariable Long id) {
    if (result.hasErrors()) {
      return validate(result);
    }

    Optional<User> existsUser = userService.findUserById(id);

    if (existsUser.isPresent()) {
      User userDb = existsUser.get();

      if (!user.getEmail().isEmpty() && !user.getEmail().equalsIgnoreCase(userDb.getEmail())
          && userService.findByEmail(user.getEmail()).isPresent()) {
        return ResponseEntity.badRequest()
            .body(Collections.singletonMap("Mensaje", "Ya existe usuario con ese correo electronico!"));
      }

      userDb.setNombre(user.getNombre());
      userDb.setEmail(user.getEmail());
      userDb.setPassword(user.getPassword());

      return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(userDb));

    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteUser(@PathVariable Long id) {
    Optional<User> existsUser = userService.findUserById(id);

    if (existsUser.isPresent()) {
      userService.deleteUserById(id);
      return ResponseEntity.noContent().build();
    }

    return ResponseEntity.notFound().build();
  }

  @GetMapping("/usuarios-por-curso")
  public ResponseEntity<?> obtenerAlumnosPorCurso(@RequestParam List<Long> ids) {
    return ResponseEntity.ok(userService.listarPorIds(ids));
  }

  private ResponseEntity<?> validate(BindingResult result) {
    Map<String, String> errores = new HashMap<>();
    result.getFieldErrors().forEach(err -> {
      errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
    });
    return ResponseEntity.badRequest().body(errores);
  }

  @GetMapping("/export/pdf")
  public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
    response.setContentType("application/pdf");
    DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
    String currentDateTime = dateFormatter.format(new Date());

    String headerKey = "Content-Disposition";
    String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
    response.setHeader(headerKey, headerValue);

    List<User> listUsers = userService.getUsers();

    PdfServiceImpl exporter = new PdfServiceImpl(userService);
    exporter.export(response);

  }

}
