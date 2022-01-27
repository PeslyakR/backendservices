package com.services.app.controller;

import java.util.List;

import com.services.app.dtos.in.departament.*;
import com.services.app.dtos.out.departament.*;
import com.services.app.exceptions.ServerException;
import com.services.app.services.DepartmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

  @Autowired
  private DepartmentService depService;

  @GetMapping("/find/{id}")
  @Secured({ "ROLE_ADMIN", "ROLE_SERVICES_MASTER" })
  public ResponseEntity<?> getDepartmentById(@PathVariable Long id) throws ServerException {
    var dep = depService.getDepartamentById(id);
    return new ResponseEntity<ExtendedDepartmentDto>(dep, HttpStatus.OK);

  }

  @PostMapping("/create")
  @Secured({ "ROLE_ADMIN", "ROLE_SERVICES_MASTER" })
  public ResponseEntity<?> createDepartament(@RequestBody CreatingDepartmentDto dto) throws ServerException {
    var newDep = depService.createDepartament(dto);
    return new ResponseEntity<ExtendedDepartmentDto>(newDep, HttpStatus.CREATED);
  }

  @PostMapping("/update")
  @Secured({ "ROLE_ADMIN", "ROLE_SERVICES_MASTER" })
  public ResponseEntity<?> updateDepartment(@RequestBody UpdatingDepartmentDto dto) throws ServerException {
    var dep = depService.updateDepartament(dto);
    return new ResponseEntity<ExtendedDepartmentDto>(dep, HttpStatus.OK);
  }

  @GetMapping("/findall")
  @Secured({ "ROLE_ADMIN", "ROLE_SERVICES_MASTER" })
  public ResponseEntity<?> getDepataments() {

    var allDeps = depService.getDepataments();
    return new ResponseEntity<List<DepartmentDto>>(allDeps, HttpStatus.OK);
  }

  @DeleteMapping("/delete/{id}")
  @Secured({ "ROLE_ADMIN", "ROLE_SERVICES_MASTER" })
  public ResponseEntity<?> deleteDepartment(@PathVariable Long id) throws ServerException {
    depService.deleteDepartanemt(id);
    return new ResponseEntity<Boolean>(true, HttpStatus.OK);
  }

  @DeleteMapping("/adm/delete/{id}")
  @Secured({ "ROLE_ADMIN", "ROLE_SERVICES_MASTER" })
  public ResponseEntity<?> adminDeleteDepartment(@PathVariable Long id) throws ServerException {
    depService.adminDeleteDepartanemt(id);
    return new ResponseEntity<Boolean>(true, HttpStatus.OK);
  }

  @GetMapping("/adm/findall")
  @Secured({ "ROLE_ADMIN", "ROLE_SERVICES_MASTER" })
  public ResponseEntity<?> getAllDepataments() {
    var allDeps = depService.getAllDepataments();
    return new ResponseEntity<List<DepartmentDto>>(allDeps, HttpStatus.OK);
  }

  @PutMapping("/adm/restore/{id}")
  @Secured({ "ROLE_ADMIN", "ROLE_SERVICES_MASTER" })
  public ResponseEntity<?> restoreDepartment(@PathVariable Long id) throws ServerException {
    var dep = depService.restoreDepartament(id);
    return new ResponseEntity<ExtendedDepartmentDto>(dep, HttpStatus.OK);
  }

}
