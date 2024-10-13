package xenhire.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import xenhire.model.ClientPreferenceTemplate;

public interface ClientPreferenceTemplateRepository extends JpaRepository<ClientPreferenceTemplate, Long>{

	@Query(value="select ifnull(max(id), 0) from client_preference_template", nativeQuery=true)
	int getMaxId();

	Page<ClientPreferenceTemplate> findAllByClientId(long clientId, Pageable paging);

}
