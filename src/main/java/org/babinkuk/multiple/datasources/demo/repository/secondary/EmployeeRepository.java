package org.babinkuk.multiple.datasources.demo.repository.secondary;

import java.util.List;

import org.babinkuk.multiple.datasources.demo.entity.secondary.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer>{

	// that's it... no need to write any code
	// optional
	public Employee findByEmail(String email);
	
	// custom method to sort by lastName
	public List<Employee> findAllByOrderByLastNameAsc();
	
	// search by name
	public List<Employee> findByFirstNameContainsOrLastNameContainsAllIgnoreCase(String name, String lName);

}
 