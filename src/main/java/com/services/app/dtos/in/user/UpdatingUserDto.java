package com.services.app.dtos.in.user;

import lombok.Data;

@Data
public class UpdatingUserDto {
    private Long id;
    private String username;
    private String password;
}
