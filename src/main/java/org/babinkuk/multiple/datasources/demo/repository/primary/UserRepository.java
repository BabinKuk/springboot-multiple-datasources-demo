package org.babinkuk.multiple.datasources.demo.repository.primary;

import org.babinkuk.multiple.datasources.demo.entity.primary.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	public User findByEmail(String email);
	
	public User findByUsername(String username);
}
