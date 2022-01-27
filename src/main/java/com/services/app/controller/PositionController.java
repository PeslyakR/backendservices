package com.services.app.controller;

import java.util.List;

import com.services.app.dtos.in.position.CreatingPostitionDto;
import com.services.app.dtos.in.position.UpdatingPositionDto;
import com.services.app.dtos.out.position.PositionDto;
import com.services.app.exceptions.ServerException;
import com.services.app.services.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/positions")
public class PositionController {

  @Autowired
  private PositionService posServ;

  @PostMapping("/create")
  @Secured({ "ROLE_ADMIN", "ROLE_SERVICES_MASTER" })
  public ResponseEntity<?> createPosition(@RequestBody CreatingPostitionDto createPos) throws ServerException {
    var newPos = posServ.createPosition(createPos);
    return new ResponseEntity<PositionDto>(newPos, HttpStatus.CREATED);
  }

  @PostMapping("/update")
  @Secured({ "ROLE_ADMIN", "ROLE_SERVICES_MASTER" })
  public ResponseEntity<?> updatePosition(@RequestBody UpdatingPositionDto dto) throws ServerException {
    var dep = posServ.updatePosition(dto);
    return new ResponseEntity<PositionDto>(dep, HttpStatus.OK);
  }

  @GetMapping("/find/{id}")
  @Secured({ "ROLE_ADMIN", "ROLE_SERVICES_MASTER" })
  public ResponseEntity<?> getPostitionBytId(@PathVariable Long id) throws ServerException {
    var position = posServ.getPositionById(id);
    return new ResponseEntity<PositionDto>(position, HttpStatus.OK);
  }

  @GetMapping("/findbydepartamentid/{idDep}")
  @Secured({ "ROLE_ADMIN", "ROLE_SERVICES_MASTER" })
  public ResponseEntity<?> getPositionsByDepartamentId(@PathVariable Long idDep) throws ServerException {
    var addPos = posServ.getPositionsByDepartamentId(idDep);
    return new ResponseEntity<List<PositionDto>>(addPos, HttpStatus.OK);
  }

  @DeleteMapping("/delete/{id}")
  @Secured({ "ROLE_ADMIN", "ROLE_SERVICES_MASTER" })
  public ResponseEntity<?> deletePosition(@PathVariable Long id) throws ServerException {
    posServ.deletePositionById(id);
    return new ResponseEntity<String>("Должность успешно удалена!", HttpStatus.OK);
  }

  @DeleteMapping("/adm/delete/{id}")
  @Secured({ "ROLE_ADMIN", "ROLE_SERVICES_MASTER" })
  public ResponseEntity<?> adminDeleteDepartment(@PathVariable Long id) throws ServerException {
    posServ.adminDeletePositionById(id);
    return new ResponseEntity<String>("Должность удалена из базы данных!", HttpStatus.OK);
  }

  @GetMapping("/adm/findbydepartamentid/{id}")
  @Secured({ "ROLE_ADMIN", "ROLE_SERVICES_MASTER" })
  public ResponseEntity<?> getAllPositionsByDepartamentId(@PathVariable Long id) throws ServerException {
    var allPos = posServ.getAllPositionsByDepartamentId(id);
    return new ResponseEntity<List<PositionDto>>(allPos, HttpStatus.OK);
  }

  @PutMapping("/adm/restore/{id}")
  @Secured({ "ROLE_ADMIN", "ROLE_SERVICES_MASTER" })
  public ResponseEntity<?> restorePosition(@PathVariable Long id) throws ServerException {
    var pos = posServ.restorePosition(id);
    return new ResponseEntity<PositionDto>(pos, HttpStatus.OK);
  }
}
