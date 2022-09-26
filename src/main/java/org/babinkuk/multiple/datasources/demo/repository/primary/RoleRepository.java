package org.babinkuk.multiple.datasources.demo.repository.primary;

import org.babinkuk.multiple.datasources.demo.entity.primary.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	
	public Role findByName(String name);
}
