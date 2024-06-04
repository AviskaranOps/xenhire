package xenhire.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import xenhire.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
	
	 Role findByName(String name);

}
