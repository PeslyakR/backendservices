package com.services.app.dtos.out.request;

import com.services.app.dictionaries.Status;
import com.services.app.dtos.out.employee.EmployeeDto;
import com.services.app.dtos.out.service.ServiceDto;
import com.services.app.entities.RequestEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestDto {

  private Long id;
  private String header;
  private String body;
  private EmployeeDto author;
  private ServiceDto service;
  private Status status;

  public RequestDto(RequestEntity entity) {
    this.id = entity.getId();
    this.header = entity.getHeader();
    this.body = entity.getBody();
    this.author = new EmployeeDto(entity.getAuthor());
    this.service = new ServiceDto(entity.getService());
    this.status = entity.getStatus();
  }
}
