package com.services.app.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;
import com.services.app.dictionaries.Status;
import com.services.app.dtos.in.InputDtoFactory;
import com.services.app.dtos.in.service.CreatingServiceDto;
import com.services.app.dtos.in.service.UpdatingServiceDto;
import com.services.app.dtos.out.service.ExtendedServiceDto;
import com.services.app.dtos.out.service.ServiceDto;
import com.services.app.entities.ServiceEntity;
import com.services.app.exceptions.ServerException;
import com.services.app.repositories.IServiceRepository;
import com.services.app.services.support.CommonItems;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceService {

  @Autowired
  private IServiceRepository serviceRepo;

  public ExtendedServiceDto createService(CreatingServiceDto dto) throws ServerException {
    CommonItems.checkTitle(dto.getTitle());
    var createdServ = serviceRepo.save(InputDtoFactory.toEntity(dto));
    return new ExtendedServiceDto(serviceRepo.save(createdServ));
  }

  @Transactional
  public ServiceDto updateService(UpdatingServiceDto dto) throws ServerException {
    System.out.println("ddt title " + dto.getTitle());
    System.out.println("ddt decr " + dto.getDescription());
    System.out.println("ddt upd " + dto.getUpdated());

    CommonItems.checkTitle(dto.getTitle());
    ServiceEntity serv = _getService(dto.getId());

    if (serv.getUpdated().equals(dto.getUpdated())) {
      ServiceEntity newServ = InputDtoFactory.updateEntity(
          _getService(dto.getId()),
          dto);
      return new ServiceDto(newServ);
    } else {
      throw new ServerException("Даннные были паралельно изменены другим пользователем во время обработки");
    }
  }

  public List<ServiceDto> getAllServices() {
    var servs = serviceRepo.findAll().stream()
        .map(
            s -> {
              return new ServiceDto(s);
            })
        .collect(Collectors.toList());
    return servs;
  }

  public ExtendedServiceDto getServiceById(Long id) throws ServerException {
    return new ExtendedServiceDto(_getService(id));
  }

  public void adminDeleteService(Long id) throws ServerException {
    serviceRepo.deleteById(id);
  }

  @Transactional
  public void deleteService(Long id) throws ServerException {
    ServiceEntity serv = _getService(id);
    serv.setStatus(Status.DELETED);
  }

  public List<ServiceDto> getServices() {
    var servs = serviceRepo.findByStatusNot(Status.DELETED).stream()
        .map(
            s -> {
              return new ServiceDto(s);
            })
        .collect(Collectors.toList());
    return servs;
  }

  @Transactional
  public ExtendedServiceDto restoreService(Long id) throws ServerException {
    ServiceEntity serv = _getService(id);
    serv.setStatus(Status.ACTIVE);
    return new ExtendedServiceDto(serv);
  }

  ServiceEntity _getService(Long id) throws ServerException {
    return serviceRepo.findById(id).orElseThrow(() -> new ServerException("Сервис не найден"));
  }

}
