package org.babinkuk.multiple.datasources.demo.service;

import java.util.List;

import org.babinkuk.multiple.datasources.demo.entity.primary.User;
import org.babinkuk.multiple.datasources.demo.entity.secondary.Employee;
import org.babinkuk.multiple.datasources.demo.repository.primary.UserRepository;
import org.babinkuk.multiple.datasources.demo.repository.secondary.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserEmployeeService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Transactional(transactionManager = "primaryTransactionManager")
	public List<User> getUsers() {
		return userRepository.findAll();
	}
	
	@Transactional(transactionManager = "secondaryTransactionManager")
	public List<Employee> getEmployees() {
		return employeeRepository.findAll();
	}
}
