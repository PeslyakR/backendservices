package com.services.app.dtos.in;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.services.app.dictionaries.Status;
import com.services.app.dtos.in.departament.*;
import com.services.app.dtos.in.employee.*;
import com.services.app.dtos.in.position.*;
import com.services.app.dtos.in.request.*;
import com.services.app.dtos.in.role.*;
import com.services.app.dtos.in.service.*;
import com.services.app.dtos.in.user.*;
import com.services.app.entities.*;

public abstract class InputDtoFactory {

  public static DepartmentEntity toEntity(CreatingDepartmentDto dto) {
    var dep = new DepartmentEntity();
    dep.setTitle(dto.getTitle());
    dep.setDescription(dto.getDescription());
    dep.setCreated(LocalDateTime.now());
    // dep.setServCreated(dto.getCreated());
    dep.setStatus(Status.ACTIVE);
    dep.setUpdated(LocalDateTime.now());

    return dep;
  }

  public static DepartmentEntity updateEntity(DepartmentEntity dep, UpdatingDepartmentDto dto) {
    dep.setId(dto.getId());
    dep.setTitle(dto.getTitle());
    dep.setDescription(dto.getDescription());
    dep.setUpdated(LocalDateTime.now());
    // dep.setServCreated(dto.getCreated());
    return dep;
  }

  public static PositionEntity toEntity(CreatingPostitionDto dto) {
    var pos = new PositionEntity();
    pos.setTitle(dto.getTitle());
    pos.setDescription(dto.getDescription());
    pos.setStatus(Status.ACTIVE);
    pos.setDepartament(dto.getDepartament());
    pos.setCreated(LocalDateTime.now());
    pos.setStatus(Status.ACTIVE);
    pos.setUpdated(LocalDateTime.now());

    return pos;

  }

  public static PositionEntity updateEntity(PositionEntity pos, UpdatingPositionDto dto) {
    pos.setId(dto.getId());
    pos.setTitle(dto.getTitle());
    pos.setDescription(dto.getDescription());
    pos.setUpdated(LocalDateTime.now());

    return pos;
  }

  public static ServiceEntity toEntity(CreatingServiceDto dto) {
    var serv = new ServiceEntity();
    serv.setTitle(dto.getTitle());
    serv.setDescription(dto.getDescription());
    serv.setCreated(LocalDateTime.now());
    serv.setStatus(Status.ACTIVE);
    serv.setUpdated(LocalDateTime.now());

    return serv;
  }

  public static ServiceEntity updateEntity(ServiceEntity serv, UpdatingServiceDto dto) {
    serv.setId(dto.getId());
    serv.setTitle(dto.getTitle());
    serv.setDescription(dto.getDescription());
    serv.setUpdated(LocalDateTime.now());

    return serv;
  }

  public static RoleEntity toEntity(CreatingRoleDto dto) {
    var role = new RoleEntity();
    role.setTitle(dto.getTitle());
    role.setDescription(dto.getDescription());
    role.setService(dto.getService());
    role.setCreated(LocalDateTime.now());
    role.setStatus(Status.ACTIVE);
    role.setName(dto.getName());
    role.setUpdated(LocalDateTime.now());

    return role;
  }

  public static RoleEntity updateEntity(RoleEntity role, UpdatingRoleDto dto) {
    role.setId(dto.getId());
    role.setTitle(dto.getTitle());
    role.setDescription(dto.getDescription());
    role.setUpdated(LocalDateTime.now());
    role.setName(dto.getName());

    return role;
  }

  public static EmployeeEntity toEntity(CreatingEmployeeDto dto) {
    EmployeeEntity emp = new EmployeeEntity();
    emp.setFullName(dto.getFullName());
    emp.setBeginWorking(dto.getBeginWorking());
    emp.setAddress(dto.getAddress());
    emp.setStatus(Status.ACTIVE);
    emp.setPosition(dto.getPosition());
    emp.setCreated(LocalDateTime.now());
    emp.setUpdated(LocalDateTime.now());
    emp.setEndWorking(LocalDate.of(2100, 12, 31));

    return emp;
  }

  public static EmployeeEntity updateEntity(EmployeeEntity emp, UpdatingEmployeeDto dto) {
    emp.setId(dto.getId());
    emp.setFullName(dto.getFullName());
    emp.setAddress(dto.getAddress());
    emp.setBeginWorking(dto.getBeginWorking());
    emp.setEndWorking(dto.getEndWorking());
    emp.setPosition(dto.getPosition());
    emp.setUpdated(LocalDateTime.now());
    return emp;
  }

  public static EmployeeEntity updateEntity(EmployeeEntity emp, AddRolesToEmployee dto) {
    emp.getRoles().addAll(dto.getRoles());
    emp.setUpdated(LocalDateTime.now());
    return emp;
  }

  public static UserEntity toEntity(CreatingUserDto dto) {
    UserEntity user = new UserEntity();
    user.setUsername(dto.getUsername());
    user.setPassword(dto.getPassword());
    user.setEmployee(dto.getEmployee());
    user.setCreated(LocalDateTime.now());
    user.setStatus(Status.ACTIVE);
    user.setUpdated(LocalDateTime.now());
    return user;
  }

  public static UserEntity updateEntity(UserEntity user, UpdatingUserDto dto) {
    user.setId(dto.getId());
    user.setUsername(dto.getUsername());
    user.setPassword(dto.getPassword());
    user.setUpdated(LocalDateTime.now());

    return user;
  }

  public static RequestEntity toEntity(CreatingRequestDto dto) {
    RequestEntity req = new RequestEntity();
    req.setHeader(dto.getHeader());
    req.setBody(dto.getBody());
    req.setStatus(Status.ACTIVE);
    req.setAuthor(dto.getEmployee());
    req.setService(dto.getService());
    req.setCreated(LocalDateTime.now());
    // initial mock entities
    req.setAdmin(dto.getEmployee());
    req.setCopywriter(dto.getEmployee());
    req.setUpdated(LocalDateTime.now());
    return req;
  }

  public static RequestEntity updateEntity(RequestEntity req, UpdatingRequestDto dto) {
    req.setId(dto.getId());
    req.setHeader(dto.getHeader());
    req.setBody(dto.getBody());
    req.setAuthor(dto.getEmployee());
    req.setService(dto.getService());

    req.setAdmin(dto.getEmployee());
    req.setAdminisratorRequest(null);
    req.setAdminisratorComment(null);
    req.setAdminResponseDate(null);

    req.setCopywriter(dto.getEmployee());
    req.setCopywriterComment(null);
    req.setCopywriterRequest(null);
    req.setCopywriterResponseDate(null);
    req.setRoles(null);

    req.setStatus(Status.ACTIVE);
    req.setUpdated(LocalDateTime.now());

    return req;
  }

  public static RequestEntity putAdminsConfirmation(RequestEntity req, ConfirmRequest dto) {

    req.setAdmin(dto.getAuthor());
    req.setAdminisratorComment(dto.getComment());
    req.setAdminisratorRequest(dto.getRequest());
    req.setAdminResponseDate(LocalDateTime.now());
    req.setUpdated(LocalDateTime.now());
    if (dto.getRoles() != null)
      req.setRoles(dto.getRoles());
    else
      req.setRoles(null);
    return req;

  }

  public static RequestEntity putCopiwritersConfirmation(RequestEntity req,
      ConfirmRequest dto) {
    req.setCopywriter(dto.getAuthor());
    req.setCopywriterComment(dto.getComment());
    req.setCopywriterRequest(dto.getRequest());
    req.setCopywriterResponseDate(LocalDateTime.now());
    req.setUpdated(LocalDateTime.now());
    if (dto.getRoles() != null)
      req.setRoles(dto.getRoles());
    else
      req.setRoles(null);
    return req;
  }
}
