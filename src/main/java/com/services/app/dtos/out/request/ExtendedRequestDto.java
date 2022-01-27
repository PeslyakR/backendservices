package com.services.app.dtos.out.request;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;
import com.services.app.dictionaries.RequestsResponse;
import com.services.app.dictionaries.Status;
import com.services.app.dtos.out.employee.EmployeeDto;
import com.services.app.dtos.out.role.RoleDto;
import com.services.app.dtos.out.service.ServiceDto;
import com.services.app.entities.RequestEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExtendedRequestDto {
  private Long id;
  private String header;
  private String body;
  private EmployeeDto author;
  private ServiceDto service;
  private Status status;
  // not initial data
  private EmployeeDto admin;
  private String adminisratorComment;
  private RequestsResponse adminisratorRequest;
  private LocalDateTime adminResponseDate;

  private EmployeeDto copywriter;
  private String copywriterComment;
  private RequestsResponse copywriterRequest;
  private LocalDateTime copywriterResponseDate;

  private Set<RoleDto> roles;

  public ExtendedRequestDto(RequestEntity entity) {
    this.id = entity.getId();
    this.header = entity.getHeader();
    this.body = entity.getBody();
    this.author = new EmployeeDto(entity.getAuthor());
    this.service = new ServiceDto(entity.getService());
    this.admin = new EmployeeDto(entity.getAdmin());
    this.adminisratorComment = entity.getAdminisratorComment();
    this.adminisratorRequest = entity.getAdminisratorRequest();
    this.adminResponseDate = entity.getAdminResponseDate();
    this.copywriter = new EmployeeDto(entity.getCopywriter());
    this.copywriterComment = entity.getCopywriterComment();
    this.copywriterRequest = entity.getCopywriterRequest();
    this.copywriterResponseDate = entity.getCopywriterResponseDate();
    this.status = entity.getStatus();

    if (entity.getRoles() != null)
      this.roles = entity.getRoles().stream()
          .filter(r -> r.getStatus() != Status.DELETED)
          .map(r -> new RoleDto(r)).collect(Collectors.toSet());

  }
}
