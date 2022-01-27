package com.services.app.dtos.in.employee;

import java.time.LocalDate;
import com.services.app.entities.*;
import lombok.Data;

@Data
public class UpdatingEmployeeDto {

    private Long id;
    private String fullName;
    private String address;
    private LocalDate beginWorking;
    private LocalDate endWorking;
    private Long idPosition;

    private PositionEntity position;
}
