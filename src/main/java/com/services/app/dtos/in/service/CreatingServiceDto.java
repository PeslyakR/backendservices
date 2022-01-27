package com.services.app.dtos.in.service;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatingServiceDto {
    private String title;
    private String description;
    private LocalDate created;
}
