package com.services.app.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import com.services.app.entities.base.BaseEntity;
import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Setter
@Getter
@Table(name = "s_employees")
@NoArgsConstructor
public class EmployeeEntity extends BaseEntity {

  @Column(name = "full_name")
  private String fullName;
  @Column(name = "adress")
  private String address;
  @Column(name = "start_working")
  private LocalDate beginWorking;
  @Column(name = "end_working")
  private LocalDate endWorking;
  @ManyToOne
  @JoinColumn(name = "id_pos")
  private PositionEntity position;
  @OneToOne(mappedBy = "employee")
  private UserEntity user;

  // #region request-employees
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "author")
  private List<RequestEntity> myRequests;
  @OneToOne(mappedBy = "admin")
  private RequestEntity requestAdmin;
  @OneToOne(mappedBy = "copywriter")
  private RequestEntity requestCopywrighter;
  // #endregion

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "s_emp_role", joinColumns = {
      @JoinColumn(name = "id_emp", referencedColumnName = "id") }, inverseJoinColumns = {
          @JoinColumn(name = "id_role", referencedColumnName = "id") })
  private Set<RoleEntity> roles;
}
