package com.services.app.controller;

import java.util.List;
import com.services.app.dtos.in.employee.*;
import com.services.app.dtos.out.employee.EmployeeDto;
import com.services.app.dtos.out.employee.ExtendedEmployeeDto;
import com.services.app.exceptions.ServerException;
import com.services.app.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

  @Autowired
  private EmployeeService empServ;

  @PostMapping("/create")
  @Secured({ "ROLE_ADMIN", "ROLE_SERVICES_MASTER" })
  public ResponseEntity<?> createEmployee(@RequestBody CreatingEmployeeDto dto) throws ServerException {
    var newEnt = empServ.createEmployee(dto);
    return new ResponseEntity<EmployeeDto>(newEnt, HttpStatus.CREATED);
  }

  @PostMapping("/update")
  @Secured({ "ROLE_ADMIN", "ROLE_SERVICES_MASTER" })
  public ResponseEntity<?> updateEmployee(@RequestBody UpdatingEmployeeDto dto) throws ServerException {
    var emp = empServ.updateEmployee(dto);
    return new ResponseEntity<ExtendedEmployeeDto>(emp, HttpStatus.OK);
  }

  @GetMapping("/findall")
  @Secured({ "ROLE_ADMIN", "ROLE_SERVICES_MASTER" })
  public ResponseEntity<?> getEmployees() {
    var emp = empServ.getEmployees();
    return new ResponseEntity<List<EmployeeDto>>(emp, HttpStatus.OK);
  }

  @GetMapping("/findByIdDep/{idDep}")
  @Secured({ "ROLE_ADMIN", "ROLE_SERVICES_MASTER" })
  public ResponseEntity<?> getEmployees(@PathVariable Long idDep) {
    var emp = empServ.getAllEmployeesByIdDep(idDep);
    return new ResponseEntity<List<EmployeeDto>>(emp, HttpStatus.OK);
  }

  @GetMapping("/findbyid/{id}")
  @Secured({ "ROLE_ADMIN", "ROLE_SERVICES_MASTER" })
  public ResponseEntity<?> getEmployeeById(@PathVariable Long id) throws ServerException {
    var emp = empServ.getEmployeeById(id);
    return new ResponseEntity<ExtendedEmployeeDto>(emp, HttpStatus.OK);
  }

  @DeleteMapping("/delete/{id}")
  @Secured({ "ROLE_ADMIN", "ROLE_SERVICES_MASTER" })
  public ResponseEntity<?> deleteEmployeeById(@PathVariable Long id) throws ServerException {
    empServ.deleteEmployeeById(id);
    return new ResponseEntity<String>("Сотрудник успешно удален!", HttpStatus.OK);
  }

  @GetMapping("/adm/findall")
  @Secured({ "ROLE_ADMIN", "ROLE_SERVICES_MASTER" })
  public ResponseEntity<?> getAllEmployees() {
    var emp = empServ.getAllEmployees();
    return new ResponseEntity<List<EmployeeDto>>(emp, HttpStatus.OK);
  }

  @DeleteMapping("/adm/delete/{id}")
  @Secured({ "ROLE_ADMIN", "ROLE_SERVICES_MASTER" })
  public ResponseEntity<?> adminDeleteEmployeeById(@PathVariable Long id) throws ServerException {
    empServ.adminDeleteEmployeeById(id);
    return new ResponseEntity<String>("Сотрудник удален из базы данных!", HttpStatus.OK);
  }

  @PutMapping("/adm/restore/{id}")
  @Secured({ "ROLE_ADMIN", "ROLE_SERVICES_MASTER" })
  public ResponseEntity<?> restoreEmployee(@PathVariable Long id) throws ServerException {
    var pos = empServ.restoreEmployee(id);
    return new ResponseEntity<ExtendedEmployeeDto>(pos, HttpStatus.OK);
  }
}
