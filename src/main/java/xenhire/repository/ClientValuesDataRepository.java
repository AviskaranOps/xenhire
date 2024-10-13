package xenhire.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import xenhire.model.ClientValueAssessmentData;

public interface ClientValuesDataRepository extends JpaRepository<ClientValueAssessmentData, Long>{

	ClientValueAssessmentData findByClientId(long clientId);

	@Query(value="select ifnull(max(id), 0) from client_value_assessment_data", nativeQuery=true)
	int getMaxId();

	Page<ClientValueAssessmentData> findByClientId(long clientId, Pageable paging);

	Page<ClientValueAssessmentData> findByClientIdAndTemplate(long clientId, boolean b, Pageable paging);

}
