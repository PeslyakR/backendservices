package com.services.app.services;

import java.util.List;
import java.util.stream.Collectors;
import com.services.app.dictionaries.Status;
import com.services.app.dtos.in.InputDtoFactory;
import com.services.app.dtos.in.role.CreatingRoleDto;
import com.services.app.dtos.in.role.UpdatingRoleDto;
import com.services.app.dtos.out.role.ExtendedRoleDto;
import com.services.app.dtos.out.role.RoleDto;
import com.services.app.entities.EmployeeEntity;
import com.services.app.entities.RoleEntity;
import com.services.app.entities.ServiceEntity;
import com.services.app.exceptions.ServerException;
import com.services.app.repositories.IRoleRepository;
import com.services.app.services.support.CommonItems;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleService {

  @Autowired
  private IRoleRepository roleRepo;
  @Autowired
  private ServiceService serviceService;

  public ExtendedRoleDto createRole(CreatingRoleDto dto) throws ServerException {
    CommonItems.checkTitle(dto.getTitle());
    ServiceEntity serv = serviceService._getService(dto.getServiceId());
    dto.setService(serv);
    var role = InputDtoFactory.toEntity(dto);

    return new ExtendedRoleDto(roleRepo.save(role));
  }

  @Transactional
  public ExtendedRoleDto updateRole(UpdatingRoleDto dto) throws ServerException {
    CommonItems.checkTitle(dto.getTitle());

    var role = _getRole(dto.getId());
    var newRole = InputDtoFactory.updateEntity(role, dto);

    return new ExtendedRoleDto(newRole);

  }

  public ExtendedRoleDto getRoleById(Long id) throws ServerException {
    RoleEntity role = _getRole(id);
    return new ExtendedRoleDto(role);
  }

  public List<RoleDto> getAllRolesByServiceId(Long id) throws ServerException {
    var roles = serviceService._getService(id)
        .getRoles().stream()
        .filter(r -> r.getStatus() != Status.DELETED)
        .map(p -> new RoleDto(p))
        .collect(Collectors.toList());

    return roles;
  }

  public List<RoleDto> getRolesByServiceId(Long id) throws ServerException {
    var roles = serviceService._getService(id)
        .getRoles().stream()
        .filter(r -> r.getStatus() != Status.DELETED)
        .map(p -> new RoleDto(p))
        .collect(Collectors.toList());
    return roles;
  }

  public ExtendedRoleDto getRoleByName(String name) throws ServerException {
    RoleEntity role = roleRepo.findByName(name);
    return new ExtendedRoleDto(role);
  }

  public void adminDeleteRole(Long id) throws ServerException {
    roleRepo.deleteById(id);
  }

  @Transactional
  public void deleteRole(Long id) throws ServerException {
    RoleEntity role = _getRole(id);
    role.setStatus(Status.DELETED);

  }

  @Transactional
  public ExtendedRoleDto restoreRole(Long id) throws ServerException {
    RoleEntity role = _getRole(id);
    checkEmployees(role.getEmployees());
    role.setStatus(Status.ACTIVE);
    return new ExtendedRoleDto(role);

  }

  RoleEntity _getRole(Long id) throws ServerException {
    return roleRepo.findById(id).orElseThrow(() -> new ServerException("Роль "));

  }

  private void checkEmployees(List<EmployeeEntity> eployees) throws ServerException {
    var activeEmployees = eployees
        .stream().filter(e -> e.getStatus() == Status.ACTIVE)
        .collect(Collectors.toList());
    if (!activeEmployees.isEmpty()) {
      throw new ServerException("Роль используется сотрудником! Удаление невозможно.");
    }
  }
}
