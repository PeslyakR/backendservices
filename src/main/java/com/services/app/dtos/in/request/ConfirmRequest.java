package com.services.app.dtos.in.request;

import java.util.Set;

import com.services.app.dictionaries.RequestsResponse;
import com.services.app.entities.EmployeeEntity;
import com.services.app.entities.RoleEntity;

import lombok.Data;

@Data
public class ConfirmRequest {
    private Long idReq;
    private Long idAuthor;
    private EmployeeEntity author;
    private String comment;
    private RequestsResponse request;

    private Set<Long> idRoles;
    private Set<RoleEntity> roles;
}
