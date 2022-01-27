package com.services.app;

import java.time.LocalDate;

import com.services.app.dtos.in.departament.CreatingDepartmentDto;
import com.services.app.dtos.in.employee.CreatingEmployeeDto;
import com.services.app.dtos.in.position.CreatingPostitionDto;
import com.services.app.dtos.in.role.CreatingRoleDto;
import com.services.app.dtos.in.service.CreatingServiceDto;
import com.services.app.dtos.in.user.CreatingUserDto;

class Initializer {

  static CreatingDepartmentDto initialDepartment() {
    var dep = new CreatingDepartmentDto(
        "Отдел администрирования",
        "Отдел администрирущий центральный сервис");

    return dep;
  }

  static CreatingPostitionDto initialPostitionWorker(Long id) {
    var pos = new CreatingPostitionDto(id, "Работник отдела", "Специалист одлета ", null);

    return pos;
  }

  static CreatingPostitionDto initialPostitionChief(Long id) {
    var pos = new CreatingPostitionDto(id, "Начальник отдела", "Специалист одлета ", null);

    return pos;
  }

  static CreatingServiceDto initialService() {
    var serv = new CreatingServiceDto(
        "Сервис сотрудников",
        "Сервис доступа к служебным приложениям",
        LocalDate.now());

    return serv;
  }

  static CreatingRoleDto initialAdminRole(Long id) {
    var role = new CreatingRoleDto(
        "ROLE_ADMIN",
        "Администратирует доступ к сервису",
        id,
        null,
        "Администратор");
    return role;
  }

  static CreatingRoleDto initialMasterRole(Long id) {
    var role = new CreatingRoleDto(
        "ROLE_SERVICES_MASTER",
        "Правообладатель сервиса",
        id,
        null,
        "Правообладатель");
    return role;
  }

  static CreatingEmployeeDto initialAdminEmployee(Long id) {
    var emp = new CreatingEmployeeDto("Иванов Иван Иванович", "Проспект Независимости, 25 кв 159", LocalDate.now(), id,
        null);

    return emp;
  }

  static CreatingEmployeeDto initialMasterEmployee(Long id) {
    var emp = new CreatingEmployeeDto("Петров Петр Петрович", "Проспект Независимости, 26 кв 158", LocalDate.now(), id,
        null);
    return emp;
  }

  static CreatingUserDto initialAdmin(Long id) {
    var adm = new CreatingUserDto("admin", "admin", id, null);
    return adm;
  }

  static CreatingUserDto initialMaster(Long id) {
    var adm = new CreatingUserDto("master", "master", id, null);

    return adm;
  }

}
