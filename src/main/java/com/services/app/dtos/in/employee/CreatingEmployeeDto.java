package com.services.app.dtos.in.employee;

import java.time.LocalDate;
import com.services.app.entities.PositionEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatingEmployeeDto {
    private String fullName;
    private String address;
    private LocalDate beginWorking;
    private Long idPosition;
    private PositionEntity position;
}
