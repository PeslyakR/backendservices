package com.services.app.services;

import java.util.List;
import java.util.stream.Collectors;
import com.services.app.dtos.out.departament.*;
import com.services.app.dictionaries.Status;
import com.services.app.dtos.in.InputDtoFactory;
import com.services.app.dtos.in.departament.*;
import com.services.app.entities.DepartmentEntity;
import com.services.app.entities.PositionEntity;
import com.services.app.exceptions.ServerException;
import com.services.app.repositories.IDepartmentRepository;
import com.services.app.services.support.CommonItems;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DepartmentService {

  @Autowired
  private IDepartmentRepository departamentRepo;

  public ExtendedDepartmentDto getDepartamentById(Long id) throws ServerException {
    return new ExtendedDepartmentDto(_getDepartament(id));
  }

  public ExtendedDepartmentDto createDepartament(CreatingDepartmentDto dep) throws ServerException {
    CommonItems.checkTitle(dep.getTitle());

    var createdDep = departamentRepo.save(InputDtoFactory.toEntity(dep));
    return new ExtendedDepartmentDto(createdDep);

  }

  public List<DepartmentDto> getAllDepataments() {
    var deps = departamentRepo.findAll().stream()
        .map(
            d -> {
              return new DepartmentDto(d);
            })
        .collect(Collectors.toList());
    return deps;
  }

  @Transactional
  public ExtendedDepartmentDto updateDepartament(UpdatingDepartmentDto dto) throws ServerException {

    CommonItems.checkTitle(dto.getTitle());
    DepartmentEntity dep = _getDepartament(dto.getId());
    if (dep.getUpdated().toString().equals(dto.getUpdated().toString())) {

      DepartmentEntity department = InputDtoFactory.updateEntity(dep, dto);
      return new ExtendedDepartmentDto(department);
    } else {
      throw new ServerException("Даннные были паралельно изменены другим пользователем во время обработки");
    }

  }

  public void adminDeleteDepartanemt(Long id) throws ServerException {
    departamentRepo.deleteById(id);
  }

  @Transactional
  public void deleteDepartanemt(Long id) throws ServerException {
    DepartmentEntity dep = _getDepartament(id);
    checkPosition(dep.getPositions());

    dep.setStatus(Status.DELETED);

  }

  public List<DepartmentDto> getDepataments() {
    var deps = departamentRepo.findByStatusNot(Status.DELETED).stream()
        .map(
            d -> {
              return new DepartmentDto(d);
            })
        .collect(Collectors.toList());

    return deps;
  }

  @Transactional
  public ExtendedDepartmentDto restoreDepartament(Long id) throws ServerException {
    DepartmentEntity dep = _getDepartament(id);
    dep.setStatus(Status.ACTIVE);

    return new ExtendedDepartmentDto(dep);
  }

  DepartmentEntity _getDepartament(Long id) throws ServerException {

    return departamentRepo.findById(id).orElseThrow(
        () -> new ServerException(
            "Управление не найдено"));
  }

  private void checkPosition(List<PositionEntity> positions) throws ServerException {
    var activePostitons = positions
        .stream().filter(e -> e.getStatus() == Status.ACTIVE)
        .collect(Collectors.toList());
    if (!activePostitons.isEmpty()) {
      throw new ServerException("Управление содержит неудаленные должности! Удаление невозможно.");
    }

  }

}
