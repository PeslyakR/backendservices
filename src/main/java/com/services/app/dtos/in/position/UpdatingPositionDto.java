package com.services.app.dtos.in.position;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UpdatingPositionDto {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime updated;
}
