package xenhire.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import xenhire.model.ClientAssessmentVersion;

public interface ClientAssessmentVersionRepository extends JpaRepository<ClientAssessmentVersion, Long>{

	ClientAssessmentVersion findByClientId(long clientId);

	
	@Query(value="select ifnull(max(version_no),0) from client_assessment_version where client_id=:clientId order by version_no desc limit 1", nativeQuery=true)
	int getMaxVersionofClient(long clientId);
	
	
	

}
