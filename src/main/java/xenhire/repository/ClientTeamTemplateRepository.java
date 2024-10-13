package xenhire.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import xenhire.model.ClientTeamTemplate;

public interface ClientTeamTemplateRepository extends JpaRepository<ClientTeamTemplate, Long>{

	
	@Query(value="select ifnull(max(id), 0) from client_team_template", nativeQuery=true)
	int getMaxId();

	Page<ClientTeamTemplate> findAllByTemplate(boolean b, Pageable paging);

}
