package com.services.app.controller;

import java.util.List;
import com.services.app.dtos.in.service.CreatingServiceDto;
import com.services.app.dtos.in.service.UpdatingServiceDto;
import com.services.app.dtos.out.service.ExtendedServiceDto;
import com.services.app.dtos.out.service.ServiceDto;
import com.services.app.exceptions.ServerException;
import com.services.app.services.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/services")
public class ServiceCrontroller {

  @Autowired
  private ServiceService servService;

  @PostMapping("/create")
  @Secured({ "ROLE_ADMIN", "ROLE_SERVICES_MASTER" })
  public ResponseEntity<?> createService(@RequestBody CreatingServiceDto serv) throws ServerException {
    var newServ = servService.createService(serv);
    return new ResponseEntity<ExtendedServiceDto>(newServ, HttpStatus.CREATED);
  }

  @PostMapping("/update")
  @Secured({ "ROLE_ADMIN", "ROLE_SERVICES_MASTER" })
  public ResponseEntity<?> updateService(@RequestBody UpdatingServiceDto dto) throws ServerException {
    var newServ = servService.updateService(dto);
    return new ResponseEntity<ServiceDto>(newServ, HttpStatus.OK);
  }

  @GetMapping("/find/{id}")
  @Secured({ "ROLE_ADMIN", "ROLE_SERVICES_MASTER" })
  public ResponseEntity<?> getServiceById(@PathVariable Long id) throws ServerException {
    var serv = servService.getServiceById(id);
    return new ResponseEntity<ExtendedServiceDto>(serv, HttpStatus.OK);
  }

  @GetMapping("/findall")
  @Secured({ "ROLE_ADMIN", "ROLE_SERVICES_MASTER" })
  public ResponseEntity<?> getServices() {
    var allServs = servService.getServices();
    return new ResponseEntity<List<ServiceDto>>(allServs, HttpStatus.OK);
  }

  @DeleteMapping("delete/{id}")
  @Secured({ "ROLE_ADMIN", "ROLE_SERVICES_MASTER" })
  public ResponseEntity<?> deleteService(@PathVariable Long id) throws ServerException {
    servService.deleteService(id);
    return new ResponseEntity<String>("Сервис успешно удален!", HttpStatus.OK);
  }

  @DeleteMapping("/adm/delete/{id}")
  @Secured({ "ROLE_ADMIN", "ROLE_SERVICES_MASTER" })
  public ResponseEntity<?> adminDeleteService(@PathVariable Long id) throws ServerException {
    servService.adminDeleteService(id);
    return new ResponseEntity<String>("Сервис удален из базы данных!", HttpStatus.OK);
  }

  @GetMapping("/adm/findall")
  @Secured({ "ROLE_ADMIN", "ROLE_SERVICES_MASTER" })
  public ResponseEntity<?> getAllServices() {
    var allServs = servService.getAllServices();
    return new ResponseEntity<List<ServiceDto>>(allServs, HttpStatus.OK);
  }

  @PutMapping("/adm/restore/{id}")
  @Secured({ "ROLE_ADMIN", "ROLE_SERVICES_MASTER" })
  public ResponseEntity<?> restoreDepartment(@PathVariable Long id) throws ServerException {
    var newServ = servService.restoreService(id);
    return new ResponseEntity<ExtendedServiceDto>(newServ, HttpStatus.OK);
  }

}
