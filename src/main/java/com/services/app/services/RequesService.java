package com.services.app.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import com.services.app.dictionaries.RequestsResponse;
import com.services.app.dictionaries.Status;
import com.services.app.dtos.in.InputDtoFactory;
import com.services.app.dtos.in.request.*;
import com.services.app.dtos.out.request.*;
import com.services.app.entities.*;
import com.services.app.exceptions.ServerException;
import com.services.app.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RequesService {

  @Autowired
  private IRequestRepository reqRepo;
  @Autowired
  private EmployeeService employeeService;
  @Autowired
  private ServiceService serviceService;
  @Autowired
  private RoleService roleService;

  public ExtendedRequestDto createRequst(CreatingRequestDto dto) throws ServerException {
    ServiceEntity service = serviceService._getService(dto.getIdService());
    EmployeeEntity author = employeeService._getEmployee(dto.getIdAuthor());
    dto.setService(service);
    dto.setEmployee(author);

    RequestEntity req = InputDtoFactory.toEntity(dto);
    return new ExtendedRequestDto(reqRepo.save(req));
  }

  @Transactional
  public ExtendedRequestDto updateRequest(UpdatingRequestDto dto) throws ServerException {

    ServiceEntity service = serviceService._getService(dto.getIdService());
    EmployeeEntity author = employeeService._getEmployee(dto.getIdAuthor());
    dto.setService(service);
    dto.setEmployee(author);
    System.out.println("DTO " + dto);
    RequestEntity req = InputDtoFactory.updateEntity(
        _getRequest(dto.getId()),
        dto);

    return new ExtendedRequestDto(req);
  }

  public List<RequestDto> getRequestsById(Long id) throws ServerException {
    List<RequestDto> requests;
    EmployeeEntity employee = employeeService._getEmployee(id);
    if (employee.getRoles().stream().anyMatch(r -> r.getTitle().equals("ROLE_ADMIN"))) {
      requests = reqRepo.findAll().stream()
          .filter(r -> r.getStatus() == Status.ACTIVE && r.getAdmin().getId() == id)
          .map(r -> new RequestDto(r))
          .collect(Collectors.toList());
    } else if (employee.getRoles().stream().anyMatch(r -> r.getTitle().equals("ROLE_SERVICES_MASTER"))) {
      requests = reqRepo.findAll().stream()
          .filter(r -> r.getStatus() == Status.ACTIVE && r.getCopywriter().getId() == id)
          .map(r -> new RequestDto(r))
          .collect(Collectors.toList());
    } else {
      requests = reqRepo.findAll().stream()
          .filter(r -> r.getStatus() == Status.ACTIVE && r.getAuthor().getId() == id)
          .map(r -> new RequestDto(r))
          .collect(Collectors.toList());
    }

    return requests;
  }

  public List<RequestDto> getRequestsForConsideration(Long idEmp) throws ServerException {
    List<RequestDto> requests;
    EmployeeEntity employee = employeeService._getEmployee(idEmp);

    if (employee.getRoles().stream().anyMatch(r -> r.getTitle().equals("ROLE_SERVICES_MASTER"))) {
      requests = reqRepo.findAll().stream()
          .filter(r -> r.getStatus() == Status.ACTIVE && r.getCopywriterRequest() == null)
          .map(r -> new RequestDto(r))
          .collect(Collectors.toList());

    } else if (employee.getRoles().stream().anyMatch(r -> r.getTitle().equals("ROLE_ADMIN"))) {
      requests = reqRepo.findAll().stream()
          .filter(r -> r.getStatus() == Status.ACTIVE
              && r.getAdminisratorRequest() == null
              && r.getCopywriterRequest() == RequestsResponse.CONFIRMED)
          .map(r -> new RequestDto(r))
          .collect(Collectors.toList());
    } else
      requests = null;

    return requests;
  }

  @Transactional
  public ExtendedRequestDto putResponse(ConfirmRequest dto)
      throws ServerException {

    var author = employeeService._getEmployee(dto.getIdAuthor());
    dto.setAuthor(author);

    Set<RoleEntity> roles = new HashSet<RoleEntity>();
    if (dto.getIdRoles() != null) {
      dto.getIdRoles().stream()
          .forEach(id -> {
            try {
              roles.add(roleService._getRole(id));
            } catch (ServerException e) {

            }
          });
      dto.setRoles(roles);
    }

    var request = _getRequest(dto.getIdReq());
    if (author.getRoles().stream().anyMatch(r -> r.getTitle().equals("ROLE_SERVICES_MASTER"))) {
      dto.setAuthor(author);
      RequestEntity req = InputDtoFactory.putCopiwritersConfirmation(request, dto);

      return new ExtendedRequestDto(req);
    } else if (request.getCopywriterRequest() != null
        && author.getRoles().stream().anyMatch(r -> r.getTitle().equals("ROLE_ADMIN"))) {
      dto.setAuthor(author);
      RequestEntity req = InputDtoFactory.putAdminsConfirmation(request, dto);

      var confAdmin = req.getAdminisratorRequest().equals(RequestsResponse.CONFIRMED);
      var confCopy = req.getCopywriterRequest().equals(RequestsResponse.CONFIRMED);
      if (confAdmin && confCopy) {

        var requester = req.getAuthor();
        requester.setRoles(roles);
      }
      return new ExtendedRequestDto(req);
    } else {

      throw new ServerException("Пользователь имеет права утверждать запросы доступа.");
    }
  }

  public void adminDeleteRequestById(Long id) throws ServerException {
    reqRepo.deleteById(id);
  }

  @Transactional
  public void deleteRequestById(Long id) throws ServerException {
    var req = _getRequest(id);
    req.setStatus(Status.DELETED);
  }

  @Transactional
  public void restoreRequestById(Long id) throws ServerException {
    var req = _getRequest(id);
    req.setStatus(Status.ACTIVE);
  }

  public ExtendedRequestDto getRequestById(Long id) throws ServerException {
    RequestEntity req = _getRequest(id);

    return new ExtendedRequestDto(req);
  }

  public List<RequestDto> getRequestsByAuthorId(Long id) throws ServerException {

    var requests = reqRepo.findAll().stream()
        .filter(r -> r.getStatus() == Status.ACTIVE && r.getAuthor().getId() == id)
        .map(r -> new RequestDto(r))
        .collect(Collectors.toList());

    return requests;
  }

  public List<RequestDto> getAll() throws ServerException {

    var requests = reqRepo.findAll().stream()
        .filter(r -> r.getStatus() == Status.ACTIVE)
        .map(r -> new RequestDto(r))
        .collect(Collectors.toList());
    return requests;
  }

  public List<RequestDto> getRequestsByServiceId(Long id) throws ServerException {
    ServiceEntity services = serviceService._getService(id);
    var requests = services.getRequests().stream()
        .filter(r -> r.getStatus() == Status.ACTIVE)
        .map(r -> new RequestDto(r))
        .collect(Collectors.toList());
    return requests;
  }

  public List<RequestDto> getAllRequestsByServiceId(Long id) throws ServerException {
    ServiceEntity services = serviceService._getService(id);
    var requests = services.getRequests().stream()
        .map(r -> new RequestDto(r))
        .collect(Collectors.toList());
    return requests;
  }

  private RequestEntity _getRequest(Long id) throws ServerException {
    return reqRepo.findById(id).orElseThrow(() -> new ServerException("Запрос не найден"));
  }
}
