package com.services.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.services.app.services.DepartmentService;
import com.services.app.services.EmployeeService;
import com.services.app.services.PositionService;
import com.services.app.services.RoleService;
import com.services.app.services.ServiceService;
import com.services.app.services.UserService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class AppApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(DepartmentService departmentService,
			PositionService positionService,
			ServiceService serviceService,
			RoleService roleService,
			EmployeeService employeeService,
			UserService userService) {

		if (departmentService.getDepataments().isEmpty()) {
			return args -> {
				var dep = departmentService.createDepartament(Initializer.initialDepartment());
				var workerPos = positionService.createPosition(Initializer.initialPostitionWorker(dep.getId()));
				var chiefPos = positionService.createPosition(Initializer.initialPostitionChief(dep.getId()));
				var serv = serviceService.createService(Initializer.initialService());
				var admRole = roleService.createRole(Initializer.initialAdminRole(serv.getId()));
				var masterRole = roleService.createRole(Initializer.initialMasterRole(serv.getId()));
				var adm = employeeService.createEmployee(Initializer.initialAdminEmployee(workerPos.getId()));
				var master = employeeService.createEmployee(Initializer.initialMasterEmployee(chiefPos.getId()));

				employeeService.addRole(adm.getId(), admRole.getId());
				employeeService.addRole(master.getId(), masterRole.getId());
				userService.register(Initializer.initialAdmin(adm.getId()));
				userService.register(Initializer.initialMaster(master.getId()));
			};
		} else {
			return null;
		}

	}

}
