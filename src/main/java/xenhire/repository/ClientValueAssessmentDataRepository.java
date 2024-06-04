package xenhire.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import xenhire.model.ClientValueAssessmentData;

public interface ClientValueAssessmentDataRepository extends JpaRepository<ClientValueAssessmentData, Long>{

	
	@Query(value="select ifnull(max(version_no),0) from client_value_assessment_data where client_id=:clientId order by version_no desc limit 1", nativeQuery=true)
	int getMaxVersion(long clientId);

	ClientValueAssessmentData findByClientId(long clientId);

}
