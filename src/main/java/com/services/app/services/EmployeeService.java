package com.services.app.services;

import java.util.List;
import java.util.stream.Collectors;
import com.services.app.dictionaries.Status;
import com.services.app.dtos.in.InputDtoFactory;
import com.services.app.dtos.in.employee.AddRolesToEmployee;
import com.services.app.dtos.in.employee.CreatingEmployeeDto;
import com.services.app.dtos.in.employee.UpdatingEmployeeDto;
import com.services.app.dtos.out.employee.EmployeeDto;
import com.services.app.dtos.out.employee.ExtendedEmployeeDto;
import com.services.app.entities.EmployeeEntity;
import com.services.app.entities.RoleEntity;
import com.services.app.exceptions.ServerException;
import com.services.app.repositories.IEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeService {

  @Autowired
  private IEmployeeRepository empRepo;
  @Autowired
  private PositionService posService;
  @Autowired
  RoleService roleService;

  public EmployeeDto createEmployee(CreatingEmployeeDto dto) throws ServerException {
    var pos = posService._getPosition(dto.getIdPosition());
    dto.setPosition(pos);
    var createdEmp = empRepo.save(InputDtoFactory.toEntity(dto));
    return new EmployeeDto(createdEmp);
  }

  @Transactional
  public ExtendedEmployeeDto updateEmployee(UpdatingEmployeeDto dto) throws ServerException {
    if (dto.getIdPosition() != null) {
      var pos = posService._getPosition(dto.getIdPosition());
      dto.setPosition(pos);
    }

    EmployeeEntity emp = InputDtoFactory.updateEntity(
        _getEmployee(dto.getId()),
        dto);

    return new ExtendedEmployeeDto(emp);
  }

  @Transactional
  public ExtendedEmployeeDto addRoles(AddRolesToEmployee dto) throws ServerException {

    EmployeeEntity pos = InputDtoFactory.updateEntity(
        _getEmployee(dto.getId()),
        dto);

    return new ExtendedEmployeeDto(pos);
  }

  @Transactional
  public void addRole(Long idEmp, Long idRole) throws ServerException {

    EmployeeEntity emp = _getEmployee(idEmp);
    RoleEntity role = roleService._getRole(idRole);
    emp.getRoles().add(role);

  }

  public List<EmployeeDto> getAllEmployees() {
    var allEmp = empRepo.findAll().stream()
        .map(e -> new EmployeeDto(e))
        .collect(Collectors.toList());

    return allEmp;
  }

  public List<EmployeeDto> getAllEmployeesByIdDep(Long idDep) {
    var allEmp = empRepo.findAll().stream()
        .filter(e -> e.getPosition().getDepartament().getId().equals(idDep))
        .map(e -> new EmployeeDto(e))
        .collect(Collectors.toList());

    return allEmp;
  }

  public List<EmployeeDto> getEmployees() {
    var allEmp = empRepo.findByStatusNot(Status.DELETED).stream()
        .map(e -> new EmployeeDto(e))
        .collect(Collectors.toList());

    return allEmp;
  }

  public ExtendedEmployeeDto getEmployeeById(Long id) throws ServerException {
    EmployeeEntity emp = _getEmployee(id);

    return new ExtendedEmployeeDto(emp);
  }

  public void adminDeleteEmployeeById(Long id) throws ServerException {
    empRepo.deleteById(id);
  }

  @Transactional
  public void deleteEmployeeById(Long id) throws ServerException {
    var emp = _getEmployee(id);
    if (emp.getUser() == null || emp.getUser().getStatus() == Status.DELETED)
      emp.setStatus(Status.DELETED);
    else
      throw new ServerException("Необходимо предварительно удалить рабочий аккаунт.");
  }

  @Transactional
  public ExtendedEmployeeDto restoreEmployee(Long id) throws ServerException {
    EmployeeEntity emp = _getEmployee(id);
    emp.setStatus(Status.ACTIVE);
    return new ExtendedEmployeeDto(emp);
  }

  EmployeeEntity _getEmployee(Long id) throws ServerException {
    return empRepo.findById(id).orElseThrow(() -> new ServerException("Сотрудник "));
  }
}
