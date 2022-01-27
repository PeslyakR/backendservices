package com.services.app.controller;

import java.util.List;
import com.services.app.dtos.in.request.*;
import com.services.app.dtos.out.request.ExtendedRequestDto;
import com.services.app.dtos.out.request.RequestDto;
import com.services.app.exceptions.ServerException;
import com.services.app.services.RequesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/requests")
public class RequestController {

  @Autowired
  private RequesService reqServ;

  @GetMapping("/findbyid/{id}")
  @Secured({ "ROLE_ADMIN", "ROLE_SERVICES_MASTER", "ROLE_USER" })
  public ResponseEntity<?> getRequestsById(@PathVariable Long id) throws ServerException {
    var req = reqServ.getRequestsById(id);
    return new ResponseEntity<List<RequestDto>>(req, HttpStatus.OK);
  }

  @GetMapping("/findRequestsForConsideration/{idEmp}")
  @Secured({ "ROLE_ADMIN", "ROLE_SERVICES_MASTER" })
  public ResponseEntity<?> getRequestsForConsideration(@PathVariable Long idEmp) throws ServerException {
    var req = reqServ.getRequestsForConsideration(idEmp);
    return new ResponseEntity<List<RequestDto>>(req, HttpStatus.OK);
  }

  @GetMapping("/findbyauthor/{idAuth}")
  @Secured({ "ROLE_ADMIN", "ROLE_SERVICES_MASTER", "ROLE_USER" })
  public ResponseEntity<?> getRequestsByAuthorId(@PathVariable Long idAuth) throws ServerException {
    var req = reqServ.getRequestsByAuthorId(idAuth);
    return new ResponseEntity<List<RequestDto>>(req, HttpStatus.OK);
  }

  @GetMapping("/findall")
  @Secured({ "ROLE_ADMIN", "ROLE_SERVICES_MASTER", "ROLE_USER" })
  public ResponseEntity<?> getRequestsByAuthorId() throws ServerException {
    var req = reqServ.getAll();
    return new ResponseEntity<List<RequestDto>>(req, HttpStatus.OK);
  }

  @PostMapping("/create")
  @Secured({ "ROLE_ADMIN", "ROLE_SERVICES_MASTER", "ROLE_USER" })
  public ResponseEntity<?> createRequest(@RequestBody CreatingRequestDto dto) throws ServerException {
    var newReq = reqServ.createRequst(dto);
    return new ResponseEntity<ExtendedRequestDto>(newReq, HttpStatus.CREATED);
  }

  @PostMapping("/update")
  @Secured({ "ROLE_ADMIN", "ROLE_SERVICES_MASTER", "ROLE_USER" })
  public ResponseEntity<?> updateRequest(@RequestBody UpdatingRequestDto dto) throws ServerException {
    var req = reqServ.updateRequest(dto);
    return new ResponseEntity<ExtendedRequestDto>(req, HttpStatus.OK);
  }

  @PutMapping("/confirmrequest")
  @Secured({ "ROLE_SERVICES_MASTER", "ROLE_ADMIN" })
  public ResponseEntity<?> confirmRequest(@RequestBody ConfirmRequest dto) throws ServerException {
    var req = reqServ.putResponse(dto);
    return new ResponseEntity<ExtendedRequestDto>(req, HttpStatus.OK);
  }

  @DeleteMapping("/delete/{id}")
  @Secured({ "ROLE_ADMIN", "ROLE_SERVICES_MASTER" })
  public ResponseEntity<?> deleteRequest(@PathVariable Long id) throws ServerException {
    reqServ.deleteRequestById(id);
    return new ResponseEntity<String>("Запрос успешно удален!", HttpStatus.OK);
  }

  @GetMapping("/find/{idReq}")
  @Secured({ "ROLE_ADMIN", "ROLE_SERVICES_MASTER", "ROLE_USER" })
  public ResponseEntity<?> getRequestById(@PathVariable Long idReq) throws ServerException {
    var req = reqServ.getRequestById(idReq);
    return new ResponseEntity<ExtendedRequestDto>(req, HttpStatus.OK);
  }

  @GetMapping("/findbyservice/{idServ}")
  @Secured({ "ROLE_ADMIN", "ROLE_SERVICES_MASTER" })
  public ResponseEntity<?> getRequestsByServiceId(@PathVariable Long idServ) throws ServerException {
    var req = reqServ.getRequestsByServiceId(idServ);
    return new ResponseEntity<List<RequestDto>>(req, HttpStatus.OK);
  }

  @DeleteMapping("/adm/delete/{id}")
  @Secured({ "ROLE_ADMIN", "ROLE_SERVICES_MASTER" })
  public ResponseEntity<?> adminDeleteRequest(@PathVariable Long id) throws ServerException {
    reqServ.adminDeleteRequestById(id);
    return new ResponseEntity<String>("Роль успешно удалена!", HttpStatus.OK);
  }

  @GetMapping("findallbyservice/{idServ}")
  @Secured({ "ROLE_ADMIN", "ROLE_SERVICES_MASTER" })
  public ResponseEntity<?> getAllRequestsByServiceId(@PathVariable Long idServ) throws ServerException {
    var req = reqServ.getAllRequestsByServiceId(idServ);
    return new ResponseEntity<List<RequestDto>>(req, HttpStatus.OK);
  }

  @PutMapping("/adm/restore/{id}")
  @Secured({ "ROLE_ADMIN", "ROLE_SERVICES_MASTER" })
  public ResponseEntity<?> restoreRequest(@PathVariable Long id) throws ServerException {
    reqServ.restoreRequestById(id);
    return new ResponseEntity<Boolean>(true, HttpStatus.OK);
  }
}
