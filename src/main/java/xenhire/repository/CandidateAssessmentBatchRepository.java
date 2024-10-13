package xenhire.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import xenhire.model.CandidateAssessmentBatch;

public interface CandidateAssessmentBatchRepository extends JpaRepository<CandidateAssessmentBatch, Long>{

	
	@Query(value="select ifnull(count(*), 0) from candidate_assessment_batch where candidate_id=:candidateId", nativeQuery=true)
	int getAssessmentsCountofCandidate(long candidateId);

	List<CandidateAssessmentBatch> findByCandidateId(long candidateId);

}
