package com.services.app.services;

import com.services.app.dtos.in.InputDtoFactory;
import com.services.app.dtos.in.user.*;
import com.services.app.dtos.out.user.*;
import com.services.app.entities.UserEntity;
import com.services.app.exceptions.ServerException;
import com.services.app.repositories.IUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

  @Autowired
  private IUserRepository userRepository;
  @Autowired
  private EmployeeService employeeService;
  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  public UserDto register(CreatingUserDto dto) throws ServerException {
    checkRegister(dto.getUsername());
    var emp = employeeService._getEmployee(dto.getIdEmployee());
    dto.setEmployee(emp);
    dto.setPassword(passwordEncoder.encode(dto.getPassword()));
    var newUser = userRepository.save(InputDtoFactory.toEntity(dto));

    return new UserDto(newUser);
  }

  @Transactional
  public UserDto updateUser(UpdatingUserDto dto) throws ServerException {
    checkRegister(dto.getUsername());
    UserEntity user = _getUser(dto.getId());
    dto.setPassword(passwordEncoder.encode(dto.getPassword()));

    InputDtoFactory.updateEntity(
        user,
        dto);
    return new UserDto(user);

  }

  public AuthUserDto findUserByUsername(String username) {
    var user = userRepository.findByUsername(username);
    return new AuthUserDto(user);
  }

  public UserDto findUserById(Long id) throws ServerException {
    var user = _getUser(id);
    return new UserDto(user);
  }

  @Transactional
  public void deleteUser(Long id) throws ServerException {
    userRepository.deleteById(id);
  }

  UserEntity _getUser(Long id) throws ServerException {
    return userRepository.findById(id).orElseThrow(() -> new ServerException("Пользователь "));
  }

  private void checkRegister(String username) throws ServerException {
    var user = userRepository.findByUsername(username);
    if (user != null)
      throw new ServerException("Пonьзователь с таким именем уже существует.");
  }
}
