package xenhire.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import xenhire.model.ClientSkills;

public interface ClientSkillsRepository extends JpaRepository<ClientSkills, Long>{

	List<ClientSkills> findByClientId(long clientId);

	
}
