package com.services.app.controller;

import java.util.List;
import com.services.app.dtos.in.role.CreatingRoleDto;
import com.services.app.dtos.in.role.UpdatingRoleDto;
import com.services.app.dtos.out.role.ExtendedRoleDto;
import com.services.app.dtos.out.role.RoleDto;
import com.services.app.exceptions.ServerException;
import com.services.app.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

  @Autowired
  private RoleService roleService;

  @PostMapping("/create")
  @Secured({ "ROLE_ADMIN", "ROLE_SERVICES_MASTER" })
  public ResponseEntity<?> createRole(@RequestBody CreatingRoleDto createRole) throws ServerException {
    var newRole = roleService.createRole(createRole);
    return new ResponseEntity<ExtendedRoleDto>(newRole, HttpStatus.CREATED);
  }

  @PostMapping("/update")
  @Secured({ "ROLE_ADMIN", "ROLE_SERVICES_MASTER" })
  public ResponseEntity<?> updateRole(@RequestBody UpdatingRoleDto dto) throws ServerException {
    var role = roleService.updateRole(dto);
    return new ResponseEntity<ExtendedRoleDto>(role, HttpStatus.OK);
  }

  @GetMapping("/find/{id}")
  @Secured({ "ROLE_ADMIN", "ROLE_SERVICES_MASTER" })
  public ResponseEntity<?> getRoleById(@PathVariable Long id) throws ServerException {
    var role = roleService.getRoleById(id);
    return new ResponseEntity<ExtendedRoleDto>(role, HttpStatus.OK);
  }

  @GetMapping("/findbyname/{name}")
  @Secured({ "ROLE_ADMIN", "ROLE_SERVICES_MASTER" })
  public ResponseEntity<?> getRoleByName(@PathVariable String name) throws ServerException {
    var role = roleService.getRoleByName(name);
    return new ResponseEntity<ExtendedRoleDto>(role, HttpStatus.OK);
  }

  @GetMapping("/findbyserviceid/{idServ}")
  @Secured({ "ROLE_ADMIN", "ROLE_SERVICES_MASTER" })
  public ResponseEntity<?> getRolesByServiceId(@PathVariable Long idServ) throws ServerException {
    var addRole = roleService.getRolesByServiceId(idServ);
    return new ResponseEntity<List<RoleDto>>(addRole, HttpStatus.OK);
  }

  @DeleteMapping("/delete/{id}")
  @Secured({ "ROLE_ADMIN", "ROLE_SERVICES_MASTER" })
  public ResponseEntity<?> deleteRole(@PathVariable Long id) throws ServerException {
    roleService.deleteRole(id);
    return new ResponseEntity<Boolean>(true, HttpStatus.OK);
  }

  @DeleteMapping("/adm/delete/{id}")
  @Secured({ "ROLE_ADMIN", "ROLE_SERVICES_MASTER" })
  public ResponseEntity<?> adminDeleteRole(@PathVariable Long id) throws ServerException {
    roleService.adminDeleteRole(id);
    return new ResponseEntity<String>("Роль удалена из базы данных!", HttpStatus.OK);
  }

  @GetMapping("/adm/findbyserviceid/{id}")
  @Secured({ "ROLE_ADMIN", "ROLE_SERVICES_MASTER" })
  public ResponseEntity<?> getRolesByIdService(@PathVariable Long id) throws ServerException {
    var allRoles = roleService.getAllRolesByServiceId(id);
    return new ResponseEntity<List<RoleDto>>(allRoles, HttpStatus.OK);
  }

  @PutMapping("/adm/restore/{id}")
  @Secured({ "ROLE_ADMIN", "ROLE_SERVICES_MASTER" })
  public ResponseEntity<?> restoreRole(@PathVariable Long id) throws ServerException {
    var role = roleService.restoreRole(id);
    return new ResponseEntity<ExtendedRoleDto>(role, HttpStatus.OK);
  }
}
