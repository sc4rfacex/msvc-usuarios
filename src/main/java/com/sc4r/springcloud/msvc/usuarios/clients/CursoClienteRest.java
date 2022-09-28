package com.sc4r.springcloud.msvc.usuarios.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-cursos")
public interface CursoClienteRest {
  @DeleteMapping("/eliminar-usuario/{id}")
  void eliminarCursoUsuarioPorId(@PathVariable Long id);

}
