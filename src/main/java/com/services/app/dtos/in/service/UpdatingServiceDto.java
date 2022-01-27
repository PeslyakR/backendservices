package com.services.app.dtos.in.service;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UpdatingServiceDto {
    private Long id;
    private String title;
    private String description;
    public LocalDateTime updated;
}
