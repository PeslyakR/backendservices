package com.services.app.services;

import java.util.stream.Collectors;

import javax.transaction.Transactional;

import java.util.List;
import com.services.app.dictionaries.Status;
import com.services.app.dtos.in.InputDtoFactory;
import com.services.app.dtos.in.position.*;
import com.services.app.dtos.out.position.*;
import com.services.app.entities.DepartmentEntity;
import com.services.app.entities.EmployeeEntity;
import com.services.app.entities.PositionEntity;
import com.services.app.exceptions.ServerException;
import com.services.app.repositories.IPositionRepository;
import com.services.app.services.support.CommonItems;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PositionService {

  @Autowired
  private IPositionRepository postionRepo;
  @Autowired
  private DepartmentService depService;

  public PositionDto createPosition(CreatingPostitionDto dto) throws ServerException {
    CommonItems.checkTitle(dto.getTitle());
    var dep = depService._getDepartament(dto.getIdDepartament());
    dto.setDepartament(dep);
    PositionDto newPos = new PositionDto(postionRepo.save(InputDtoFactory.toEntity(dto)));

    return newPos;
  }

  public PositionDto getPositionById(Long id) throws ServerException {
    var pos = _getPosition(id);
    return new PositionDto(pos);
  }

  public List<PositionDto> getAllPositionsByDepartamentId(Long idDep) throws ServerException {
    DepartmentEntity dep = depService._getDepartament(idDep);
    var positions = dep.getPositions().stream()
        .map(p -> {
          return new PositionDto(p);
        }).collect(Collectors.toList());

    return positions;
  }

  public List<PositionDto> getPositionsByDepartamentId(Long idDep) throws ServerException {
    DepartmentEntity dep = depService._getDepartament(idDep);
    var positions = dep.getPositions().stream()
        .filter(p -> p.getStatus() != Status.DELETED)
        .map(p -> new PositionDto(p)).collect(Collectors.toList());

    return positions;
  }

  @Transactional
  public PositionDto updatePosition(UpdatingPositionDto dto) throws ServerException {
    CommonItems.checkTitle(dto.getTitle());
    PositionEntity pos = _getPosition(dto.getId());
    if (pos.getUpdated().equals(dto.getUpdated())) {
      PositionEntity position = InputDtoFactory.updateEntity(_getPosition(dto.getId()), dto);
      return new PositionDto(position);
    } else {
      throw new ServerException("Даннные были паралельно изменены другим пользователем во время обработки");
    }
  }

  public void adminDeletePositionById(Long id) throws ServerException {
    postionRepo.deleteById(id);
  }

  @Transactional
  public void deletePositionById(Long id) throws ServerException {
    System.out.println("deletePositionById " + id);
    var pos = _getPosition(id);
    checkEmployees(pos.getEmployees());

    pos.setStatus(Status.DELETED);
  }

  @Transactional
  public PositionDto restorePosition(Long id) throws ServerException {
    PositionEntity pos = _getPosition(id);
    pos.setStatus(Status.ACTIVE);
    return new PositionDto(pos);
  }

  PositionEntity _getPosition(Long id) throws ServerException {
    PositionEntity pos = postionRepo.findById(id).orElseThrow(() -> new ServerException("Должность не найдена"));
    return pos;
  }

  private void checkEmployees(List<EmployeeEntity> eployees) throws ServerException {
    var activeEmployees = eployees
        .stream().filter(e -> e.getStatus() == Status.ACTIVE)
        .collect(Collectors.toList());
    if (!activeEmployees.isEmpty()) {
      throw new ServerException("Должность используется сотрудником! Удаление невозможно.");
    }
  }

}
