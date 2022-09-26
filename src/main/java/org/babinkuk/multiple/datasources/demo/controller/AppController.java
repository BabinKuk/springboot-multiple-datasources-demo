package org.babinkuk.multiple.datasources.demo.controller;

import java.util.List;

import org.babinkuk.multiple.datasources.demo.entity.primary.User;
import org.babinkuk.multiple.datasources.demo.entity.secondary.Employee;
import org.babinkuk.multiple.datasources.demo.service.UserEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AppController {
	
	@Autowired
	private UserEmployeeService userEmployeeService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getAllData(Model model) {
		
		List<User> users = userEmployeeService.getUsers();
		List<Employee> employees = userEmployeeService.getEmployees();

		model.addAttribute("users", users);
		model.addAttribute("employees", employees);
		
		return "index";
	}

}
