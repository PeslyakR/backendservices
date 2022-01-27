package com.services.app.entities;

import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.*;
import com.services.app.dictionaries.RequestsResponse;
import com.services.app.entities.base.BaseEntity;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "s_requsts")
public class RequestEntity extends BaseEntity {

  private String header;
  private String body;
  @Column(name = "adm_com")
  private String adminisratorComment;

  @Enumerated(EnumType.STRING)
  @Column(name = "adm_req")
  private RequestsResponse adminisratorRequest;

  private LocalDateTime adminResponseDate;
  @Column(name = "copy_com")
  private String copywriterComment;

  @Enumerated(EnumType.STRING)
  @Column(name = "copy_req")
  private RequestsResponse copywriterRequest;
  private LocalDateTime copywriterResponseDate;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "s_req_role", joinColumns = {
      @JoinColumn(name = "id_req", referencedColumnName = "id") }, inverseJoinColumns = {
          @JoinColumn(name = "id_role", referencedColumnName = "id") })
  private Set<RoleEntity> roles;

  @ManyToOne
  @JoinColumn(name = "id_serv")
  private ServiceEntity service;
  @ManyToOne
  @JoinColumn(name = "id_auth")
  private EmployeeEntity author;
  @OneToOne
  @JoinColumn(name = "id_adm")
  private EmployeeEntity admin;
  @OneToOne
  @JoinColumn(name = "id_copy")
  private EmployeeEntity copywriter;
}
