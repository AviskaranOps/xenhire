package xenhire.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import xenhire.model.ClientAssessmentBatch;

public interface ClientAssessmentBatchRepository extends JpaRepository<ClientAssessmentBatch, Long>{

	ClientAssessmentBatch findByClientIdAndBatchName(long clientId, String batchName);


	//Page<ClientAssessmentBatch> getAllWithPagination(long clientId, Pageable paging);


	Page<ClientAssessmentBatch> findByClientId(long clientId, Pageable paging);


	@Query(value="select count(*) from client_assessment_batch where client_id=:clientId", nativeQuery=true)
	int getAssessmentsCount(long clientId);
}
