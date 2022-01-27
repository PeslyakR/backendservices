package com.services.app.controller;

import com.services.app.dtos.in.user.CreatingUserDto;
import com.services.app.dtos.in.user.UpdatingUserDto;
import com.services.app.dtos.out.user.UserDto;
import com.services.app.exceptions.ServerException;
import com.services.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

  @Autowired
  private UserService userService;

  @PostMapping("/register")
  @Secured({ "ROLE_ADMIN", "ROLE_SERVICES_MASTER" })
  public ResponseEntity<?> registerUser(@RequestBody CreatingUserDto createUser)
      throws ServerException {
    var user = userService.register(createUser);
    return new ResponseEntity<UserDto>(user, HttpStatus.CREATED);
  }

  @PutMapping("/update")
  @Secured({ "ROLE_ADMIN", "ROLE_SERVICES_MASTER" })
  public ResponseEntity<?> updateUser(@RequestBody UpdatingUserDto dto)
      throws ServerException {
    var user = userService.updateUser(dto);
    return new ResponseEntity<UserDto>(user, HttpStatus.OK);
  }

  @GetMapping("/finduserbyid/{id}")
  @Secured({ "ROLE_ADMIN", "ROLE_SERVICES_MASTER" })
  public ResponseEntity<?> getUserById(@PathVariable Long id) throws ServerException {
    var user = userService.findUserById(id);
    return new ResponseEntity<UserDto>(user, HttpStatus.OK);
  }

  @DeleteMapping("/delete/{id}")
  @Secured({ "ROLE_ADMIN", "ROLE_SERVICES_MASTER" })
  public ResponseEntity<?> deleteUser(@PathVariable Long id) throws ServerException {
    userService.deleteUser(id);
    return new ResponseEntity<Boolean>(true, HttpStatus.OK);
  }

}
