package com.services.app.dtos.out.service;

import com.services.app.dictionaries.Status;
import com.services.app.entities.ServiceEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ServiceDto {
  private Long id;
  private String title;
  private String description;
  private Status status;

  public ServiceDto(ServiceEntity serv) {
    this.id = serv.getId();
    this.title = serv.getTitle();
    this.description = serv.getDescription();
    this.status = serv.getStatus();

  }

}
